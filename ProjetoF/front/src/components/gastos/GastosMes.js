// GastosMes.js
import React, { useEffect, useState } from "react";
import Tabela from "../Tabela";
import Formulario from "../Formulario";




function GastosMes() {
  const [anos, setAnos] = useState([]);
  const [meses, setMeses] = useState([]);
  const [anoSelecionado, setAnoSelecionado] = useState('');
  const [mesSelecionado, setMesSelecionado] = useState('');
  const [dadosTabela, setDadosTabela] = useState([]);
  const [somaGastos, setSomaGastos] = useState(null);
  const [somaTotal, setTotal] = useState(null);
  const[btnCadastrar, setBtnCadastrar] = useState(false);
  const[gastos, setGastos] = useState([]);

  const gasto = {
    
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
         setObjGasto({ ...objGasto, [e.target.name]: e.target.value });
       }
     } else {
       setObjGasto({ ...objGasto, [e.target.name]: e.target.value });
     }
   }
    
    //receber os dados digitados
    const [objGasto, setObjGasto] = useState(gasto);

  const buscarDadosTabela = () => {
    if (anoSelecionado && mesSelecionado) {
      fetch('https://a3-engenhariadesoftware.onrender.com/listargastos/'+anoSelecionado+'/'+mesSelecionado)
        .then(retorno => retorno.json())
        .then(retorno_convertido => {
          setDadosTabela(retorno_convertido);
        })
        .catch(erro => console.error('Erro ao buscar dados:', erro));

      fetch('https://a3-engenhariadesoftware.onrender.com/somadosgasto/'+anoSelecionado+'/'+mesSelecionado)
        .then(retorno => retorno.json())
        .then(retorno_convertido => {
          setSomaGastos(retorno_convertido);
        })
        .catch(erro => console.error('Erro ao buscar soma dos gastos:', erro));

        fetch('https://a3-engenhariadesoftware.onrender.com/totalMensal/'+anoSelecionado+'/'+mesSelecionado)
        .then(retorno => retorno.json())
        .then(retorno_convertido => {
          setTotal(retorno_convertido);
        })
        .catch(erro => console.error('Erro ao buscar total:', erro));
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
    
      setObjGasto(gastos[indice]);
      setBtnCadastrar(false);
    
  }

    //limpar formulário
    const limparFormulario = () =>{
      setObjGasto(gasto);
      setBtnCadastrar(true);
    }
    //useEffect listargastos
  useEffect(() => {

    //requisita dados do link
    fetch("https://a3-engenhariadesoftware.onrender.com/listargastos")


    //.then os dados recebidos precisam ser transformados em jason
    .then(retorno => retorno.json())
    .then(retorno_convertido => setGastos(retorno_convertido));



    
  },[]/* os "[]"  são para evitar que seja feito a requicição infinitamente (sem eles a requicição entra em looping)*/);

  //obtendo os dados digitados

    const remover = () => {
      fetch('https://a3-engenhariadesoftware.onrender.com/removergasto/'+objGasto.codigo,/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
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
  //alterar Gasto
  const alterar = () => {
    fetch('https://a3-engenhariadesoftware.onrender.com/alterargasto',/* normalmente fetch requisita funções GET mas como sera requisitado outra forma de methodo a ",{}" para passar as caracteristicas complementares*/{
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
      <Formulario eventoTeclado={aoDigitar} obj ={objGasto} cancelar={limparFormulario} remover={remover} alterar={alterar}/>
      <Tabela vetor={dadosTabela} selecionar={selecionarProduto} />
      {somaGastos !== null && (
        <h2>O gasto total de {mesSelecionado}/{anoSelecionado} foi: R$ {somaGastos}</h2>
      )}
      {somaTotal !== null && (
        <h2>O total do mes {mesSelecionado}/{anoSelecionado} foi: R$ {somaTotal.toFixed(2)}</h2>
      )}
    </div>
  );
}



export default GastosMes;
