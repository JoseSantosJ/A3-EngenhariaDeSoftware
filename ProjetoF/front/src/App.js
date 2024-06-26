import './App.css';
import{BrowserRouter, Routes, Link, Route} from 'react-router-dom'
import Gastos from './components/gastos/Gastos';
import Entradas from './components/entradas/Entradas';
import GastosMes from './components/gastos/GastosMes';
import EntradasMes from './components/entradas/EntradasMes';
 



function App() {
  return (
    
    <div >
      <BrowserRouter>
      <h1>Controlador Financeiro</h1>
      <ul>
        <li><Link to="">Tela inicial</Link></li>      
        <li><Link to="/telaDeCadastro">Gastos</Link>
        <li><Link to="/gastosMensais">Gastos por mes</Link></li>
        </li>
        <li><Link to="/telaDeEntradas">Entradas</Link>
        <li><Link to="/entradasMensais">Entradas por mes</Link></li>
        </li>
    </ul>
    <Routes>
      <Route path='/telaDeCadastro' element={<Gastos/>}></Route>
      <Route path='/telaDeEntradas' element={<Entradas/>}></Route>
      <Route path='/gastosMensais' element={<GastosMes/>}></Route>
      <Route path='/entradasMensais' element={<EntradasMes/>}></Route>
    </Routes>
    </BrowserRouter>
    </div>
    
  );
}

export default App;
