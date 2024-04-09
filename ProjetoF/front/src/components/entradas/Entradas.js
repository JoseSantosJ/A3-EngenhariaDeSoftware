import { useEffect, useState } from 'react';
import Formulario from './Formulario';
import Tabela from '../Tabela';
import SumEntradas from './SumEntradas';


function Entradas(){
    //objeto Entrada
  const entrada = {
    codigo:0,
    data:'',
    motivo:'',
    valor:''

  }

  //sumEntradas
  const valorEntradas = SumEntradas();
 
  //useState

  //exibir/ocultar botões
  const[btnCadastrar, setBtnCadastrar] = useState(true);

  //receber dados dos Entradas(requisitados do back-end)
  const[entradas, setEntradas] = useState([]);

  //receber dados dos entradas(requisitados do back-end)
  const[sumEntrada, setSumEntrada] = useState([]);

  //receber os dados digitados
  const [objEntrada, setObjEntrada] = useState(entrada);

  //useEffect soma das entradas
  useEffect(() => {

    //useEffect requisita dados do link
    fetch("https://a3-engenhariadesoftware.onrender.com/entradas/soma")


    //.then os dados recebidos precisam ser transformados em jason
    .then(retorno => retorno.json())
    .then(retorno_convertido => setSumEntrada(retorno_convertido));



    
  },[]/* os "[]"  são para evitar que seja feito a requicição infinitamente (sem eles a requicição entra em looping)*/);

  //useEffect listar Entrada
  useEffect(() => {

    //requisita dados do link
    fetch("https://a3-engenhariadesoftware.onrender.com/gastos")


    //.then os dados recebidos precisam ser transformados em jason
    .then(retorno => retorno.json())
    .then(retorno_convertido => setEntradas(retorno_convertido));



    
  },[]/* os "[]"  são para evitar que seja feito a requicição infinitamente (sem eles a requicição entra em looping)*/);

//obtendo os dados digitados
const aoDigitar = (e) => {
  if (e.target.name === 'data') {
    // Limita a entrada da data a 10 caracteres
    if (e.target.value.length <= 10) {
      // Adiciona a barra automaticamente após digitar os dois e quatro primeiros números
      if (e.target.value.length === 2 || e.target.value.length === 5) {
        e.target.value += '/';
      }
      setObjEntrada({ ...objEntrada, [e.target.name]: e.target.value });
    }
  } else {
    setObjEntrada({ ...objEntrada, [e.target.name]: e.target.value });
  }
}

  
 
  //cadastrar Entrada
  const cadastrar = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/entradas/cadastrar',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
      method: 'post',
      body:JSON.stringify(objEntrada),
      headers:{
        'Content-type':'application/json',
        'accept':'application/json'
      }
    })
    //retorno da promessa(promessa= que sera convertido para json)
    .then(retorno => retorno.json())
    .then(retorno_convertido => {

      //caso receba uma mensagem de erro exiba um alert com a mensagem
      if(retorno_convertido.mensagem !== undefined){
        alert(retorno_convertido.mensagem);
      }else{
        setEntradas([...entradas, retorno_convertido]);
        alert('movimentação cadastrado com sucesso');
        limparFormulario();
      }

    })
  }

  //Remover entrada
  const remover = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/entradas/remover/'+objEntrada.codigo,/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
      method: 'delete',
      headers:{
        'Content-type':'application/json',
        'accept':'application/json'
      }
    })
    //retorno da promessa(promessa= que sera convertido para json)
    .then(retorno => retorno.json())
    .then(retorno_convertido => {

      // Mensagem
      alert(retorno_convertido.mensagem)

      //copia do vetor de entradas
      let vetorTemp = [...entradas];
      
      //indice
      let indice = vetorTemp.findIndex((g) => {
        return g.codigo === objEntrada.codigo;
      });

      //Remover produto do vetor temp
      vetorTemp.splice(indice, 1);

      //atualizar o vetor de entradas
      setEntradas(vetorTemp);

      limparFormulario();

    })
  }

  //alterar Entrada
  const alterar = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/entradas/alterar',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
      method: 'put',
      body:JSON.stringify(objEntrada),
      headers:{
        'Content-type':'application/json',
        'accept':'application/json'
      }
    })
    //retorno da promessa(promessa= que sera convertido para json)
    .then(retorno => retorno.json())
    .then(retorno_convertido => {

      //caso receba uma mensagem de erro exiba um alert com a mensagem
      if(retorno_convertido.mensagem !== undefined){
        alert(retorno_convertido.mensagem);
      }else{
        //mensagem
        alert('movimentação alterada com sucesso')

         //copia do vetor de entradas
      let vetorTemp = [...entradas];
      
      //indice
      let indice = vetorTemp.findIndex((g) => {
        return g.codigo === objEntrada.codigo;
      });

      //alterar produto do vetor temp
      vetorTemp[indice] = objEntrada;

      //atualizar o vetor de entradas
      setEntradas(vetorTemp);

        limparFormulario();
      }

    })
  }

  //limpar formulário
  const limparFormulario = () =>{
    setObjEntrada(entrada);
    setBtnCadastrar(true);
  }

  //selecionar produto
  const selecionarProduto = (indice) =>{
    setObjEntrada(entradas[indice]);
    setBtnCadastrar(false);
  }

    return(
        <div>
          <h1>Entradas</h1>
            <Formulario botao ={btnCadastrar} eventoTeclado={aoDigitar} cadastrar={cadastrar} obj ={objEntrada} cancelar={limparFormulario} remover={remover} alterar={alterar} ></Formulario>
            <Tabela vetor={entradas} selecionar={selecionarProduto}/>
            <h2>Total Recebido: R$ {SumEntradas()}</h2>
        </div>
    )
}

export default Entradas;