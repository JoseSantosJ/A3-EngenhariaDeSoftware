// entradasMes.js
import React, { useEffect, useState } from "react";
import Tabela from "../Tabela";
import Formulario from "../Formulario";




function EntradasMes() {
  const [anos, setAnos] = useState([]);
  const [meses, setMeses] = useState([]);
  const [anoSelecionado, setAnoSelecionado] = useState('');
  const [mesSelecionado, setMesSelecionado] = useState('');
  const [dadosTabela, setDadosTabela] = useState([]);
  const [somaentradas, setSomaentradas] = useState(null);
  const [somaTotal, setTotal] = useState(null);
  const[btnCadastrar, setBtnCadastrar] = useState(false);
  const[entradas, setentradas] = useState([]);

  const entrada = {
    
    codigo:0,
    data:'',
    motivo:'',
    valor:''

  }

    //obtendo os dados digitados
    const aoDigitar = (e) => {
      //caso o campo que esteja sendo digitado for igual ao campo data 
     if (e.target.name === 'data') {
       // Limita a entrada da data a 10 caracteres
       if (e.target.value.length <= 10) {
         // Adiciona a barra automaticamente após digitar os dois e quarto primeiros números
         if (e.target.value.length === 2 || e.target.value.length === 5) {
           e.target.value += '/';
         }
         setObjentrada({ ...objentrada, [e.target.name]: e.target.value });
       }
     } else {
       setObjentrada({ ...objentrada, [e.target.name]: e.target.value });
     }
   }
    
    //receber os dados digitados
    const [objentrada, setObjentrada] = useState(entrada);

  const buscarDadosTabela = () => {
    if (anoSelecionado && mesSelecionado) {
      fetch('https://a3-engenhariadesoftware.onrender.com/entradas/'+anoSelecionado+'/'+mesSelecionado)
        .then(retorno => retorno.json())
        .then(retorno_convertido => {
          setDadosTabela(retorno_convertido);
        })
        .catch(erro => console.error('Erro ao buscar dados:', erro));

      fetch('https://a3-engenhariadesoftware.onrender.com/entradas/soma/'+anoSelecionado+'/'+mesSelecionado)
        .then(retorno => retorno.json())
        .then(retorno_convertido => {
          setSomaentradas(retorno_convertido);
        })
        .catch(erro => console.error('Erro ao buscar soma dos entradas:', erro));

        fetch('https://a3-engenhariadesoftware.onrender.com/totalMensal/'+anoSelecionado+'/'+mesSelecionado)
        .then(retorno => retorno.json())
        .then(retorno_convertido => {
          setTotal(retorno_convertido);
        })
        .catch(erro => console.error('Erro ao buscar soma dos entradas:', erro));
    } else {
      // Trate o caso em que o usuário não selecionou ano ou mês
      console.error('Ano e mês devem ser selecionados.');
    }
  };

  useEffect(() => {
    // Obter anos disponíveis (pode ser ajustado conforme necessário)
    const anosDisponiveis = [];
    const anoAtual = new Date().getFullYear();

    for (let i = (anoAtual + 30 ); i >= anoAtual - 35; i--) {
      anosDisponiveis.push(i);
    }

    setAnos(anosDisponiveis);

    // Obter meses disponíveis
    const mesesDisponiveis = Array.from({ length: 12 }, (_, index) => index + 1);
    setMeses(mesesDisponiveis);
  }, []);

  const aoSelecionarAno = (event) => {
    setAnoSelecionado(event.target.value);
  };

  const aoSelecionarMes = (event) => {
    setMesSelecionado(event.target.value);
  };

  const selecionarProduto = (indice) =>{
    
      setObjentrada(entradas[indice]);
      setBtnCadastrar(false);
    
  }

    //limpar formulário
    const limparFormulario = () =>{
      setObjentrada(entrada);
      setBtnCadastrar(true);
    }
    //useEffect listarentradas
  useEffect(() => {

    //requisita dados do link
    fetch("https://a3-engenhariadesoftware.onrender.com/entradas")


    //.then os dados recebidos precisam ser transformados em jason
    .then(retorno => retorno.json())
    .then(retorno_convertido => setentradas(retorno_convertido));



    
  },[]/* os "[]"  são para evitar que seja feito a requicição infinitamente (sem eles a requicição entra em looping)*/);

  //obtendo os dados digitados

    const remover = () => {
      fetch('https://a3-engenhariadesoftware.onrender.com/entradas/remover/'+objentrada.codigo,/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
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
          return g.codigo === objentrada.codigo;
        });
  
        //Remover produto do vetor temp
        vetorTemp.splice(indice, 1);
  
        //atualizar o vetor de entradas
        setentradas(vetorTemp);
  
        limparFormulario();
  
      })
    }
  
          //alterar entrada
  //alterar entrada
  const alterar = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/entradas/alterar',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
      method: 'put',
      body:JSON.stringify(objentrada),
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

         //copia do vetor de entradas
      let vetorTemp = [...entradas];
      
      //indice
      let indice = vetorTemp.findIndex((g) => {
        return g.codigo === objentrada.codigo;
      });

      //alterar produto do vetor temp
      vetorTemp[indice] = objentrada;

      //atualizar o vetor de entradas
      setentradas(vetorTemp);

        limparFormulario();
      }

    })
  }


  


  
  

  return (
    <div>
      <label>Ano:</label>
      <select value={anoSelecionado} onChange={aoSelecionarAno}>
        <option value="">Selecione o Ano</option>
        {anos.map((ano) => (
          <option key={ano} value={ano}>
            {ano}
          </option>
        ))}
      </select>

      <label>Mês:</label>
      <select value={mesSelecionado} onChange={aoSelecionarMes}>
        <option value="">Selecione o Mês</option>
        {meses.map((mes) => (
          <option key={mes} value={mes}>
            {mes}
          </option>
        ))}
      </select>

      <button onClick={buscarDadosTabela}>Buscar Dados</button>
      <Formulario eventoTeclado={aoDigitar} obj ={objentrada} cancelar={limparFormulario} remover={remover} alterar={alterar}/>
      <Tabela vetor={dadosTabela} selecionar={selecionarProduto} />
      {somaentradas !== null && (
        <h2>O entrada total de {mesSelecionado}/{anoSelecionado} foi: R$ {somaentradas}</h2>
      )}
      {somaTotal !== null && (
        <h2>O total do mes {mesSelecionado}/{anoSelecionado} foi: R$ {somaTotal.toFixed(2)}</h2>
      )}
    </div>
  );
}



export default EntradasMes;
