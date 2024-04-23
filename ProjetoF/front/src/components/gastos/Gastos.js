import { useEffect, useState } from 'react';
import Formulario from './Formulario';
import Tabela from '../Tabela';
import SumGastos from './SumGastos';

function Gastos(){
    //objeto gasto
  const gasto = {
    
    codigo:0,
    data:'',
    datac:'',
    valordp:'',
    motivo:'',
    valor:'',
    ndp:'',
    codigofonte:0

  }

  //SumGastos
  const valorGastos = SumGastos();


  //useState
  //exibir/ocultar parcelamento
  const[btnparcelamento, setbtnparcelamento] = useState(false);
  //exibir/ocultar botões
  const[btnCadastrar, setBtnCadastrar] = useState(true);

  //receber dados dos gastos(requisitados do back-end)
  const[gastos, setGastos] = useState([]);

    //receber dados das fontes(requisitados do back-end)
  const[Fontes, setFontes] = useState([]);

  const [fonteSelecionada, setFonteSelecionada] = useState('');

  //receber os dados digitados
  const [objGasto, setObjGasto] = useState(gasto);

  
  //useEffect listargastos
  useEffect(() => {

    //requisita dados do link
    fetch("https://a3-engenhariadesoftware.onrender.com/gastos")


    //.then os dados recebidos precisam ser transformados em jason
    .then(retorno => retorno.json())
    .then(retorno_convertido => setGastos(retorno_convertido));



    
  },[]/* os "[]"  são para evitar que seja feito a requicição infinitamente (sem eles a requicição entra em looping)*/);



  useEffect(() => {

    //requisita dados do link
    fetch("https://a3-engenhariadesoftware.onrender.com/fontes")


    //.then os dados recebidos precisam ser transformados em jason
    .then(retorno => retorno.json())
    .then(retorno_convertido => setFontes(retorno_convertido));



    
  },[]/* os "[]"  são para evitar que seja feito a requicição infinitamente (sem eles a requicição entra em looping)*/);



  const aoSelecionarFonte = (e) => {
    setObjGasto({ ...objGasto, codigofonte: e.target.value });
    setFonteSelecionada(e.target.value);
  }





  //obtendo os dados digitados
  const aoDigitar = (e) => {
   //caso o campo que esteja sendo digitado for igual ao campo data 
  if (e.target.name === 'data' || e.target.name == 'datac') {
    // Limita a entrada da data a 10 caracteres
    if (e.target.value.length <= 10) {
      // Adiciona a barra automaticamente após digitar os dois e quarto primeiros números
      if (e.target.value.length === 2 || e.target.value.length === 5) {
        e.target.value += '/';
      }
      setObjGasto({ ...objGasto, [e.target.name]: e.target.value });
    }
  } else {
    setObjGasto({ ...objGasto, [e.target.name]: e.target.value });
  }
}
 
  //cadastrar Gasto
  const cadastrar = () => {
    if(btnparcelamento == false){
      if (objGasto.codigofonte !== 0){
        objGasto.fonte = objGasto.codigofonte;
        fetch('https://a3-engenhariadesoftware.onrender.com/gastos/cadastrargastocredito/'+objGasto.codigofonte,/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
          method: 'post',
          body:JSON.stringify(objGasto),
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
            setGastos([...gastos, retorno_convertido]);
            alert('produto cadastrado com sucesso');
            limparFormulario();
          }

        })
      }else{
        fetch('https://a3-engenhariadesoftware.onrender.com/gastos/cadastrar',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
          method: 'post',
          body:JSON.stringify(objGasto),
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
            setGastos([...gastos, retorno_convertido]);
            alert('produto cadastrado com sucesso');
            limparFormulario();
          }

        })

      }

    }else{
      fetch('https://a3-engenhariadesoftware.onrender.com/gastos/cadastrarparcelamento',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
        method: 'post',
        body:JSON.stringify(objGasto),
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
          setGastos([...gastos, retorno_convertido]);
          alert('produto parcelado cadastrado com sucesso');
          limparFormulario();
        }

      })
    }
  }

  //Remover Gasto
  const remover = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/gastos/remover/'+objGasto.codigo,/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
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

      //copia do vetor de gastos
      let vetorTemp = [...gastos];
      
      //indice
      let indice = vetorTemp.findIndex((g) => {
        return g.codigo === objGasto.codigo;
      });

      //Remover produto do vetor temp
      vetorTemp.splice(indice, 1);

      //atualizar o vetor de Gastos
      setGastos(vetorTemp);

      limparFormulario();

    })
  }

  //alterar Gasto
  const alterar = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/gastos/alterar',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
      method: 'put',
      body:JSON.stringify(objGasto),
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
        alert('produto alterado com sucesso')

         //copia do vetor de gastos
      let vetorTemp = [...gastos];
      
      //indice
      let indice = vetorTemp.findIndex((g) => {
        return g.codigo === objGasto.codigo;
      });

      //alterar produto do vetor temp
      vetorTemp[indice] = objGasto;

      //atualizar o vetor de Gastos
      setGastos(vetorTemp);

        limparFormulario();
      }

    })
  }

  

  //limpar formulário
  const limparFormulario = () =>{
    setObjGasto(gasto);
    setBtnCadastrar(true);
  }

  //selecionar produto
  const selecionarProduto = (indice) =>{
    setObjGasto(gastos[indice]);
    setBtnCadastrar(false);
  }

  const tparcelamento =() =>{
    setbtnparcelamento(true)
  }

  const tcompra =() =>{
    setbtnparcelamento(false)
  }

  

  
    return(
        <div>
            <h1>Gastos</h1>
            <Formulario tparcelamento={tparcelamento} tcompra={tcompra} parcelamento={btnparcelamento} botao ={btnCadastrar} eventoTeclado={aoDigitar} cadastrar={cadastrar} obj ={objGasto} cancelar={limparFormulario} remover={remover} alterar={alterar}  Fontes ={Fontes} fonteSelecionada={fonteSelecionada} setFonteSelecionada={setFonteSelecionada} aoSelecionarFonte={aoSelecionarFonte}> </Formulario>
            <Tabela vetor={gastos} selecionar={selecionarProduto}/>
            {SumGastos() !== 'NaN' && (
              <h2> Total  Gastos: R$ {SumGastos()}</h2>
            )}
        </div>
    )
}

export default Gastos;