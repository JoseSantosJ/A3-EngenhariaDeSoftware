import './App.css';
import{BrowserRouter, Routes, Link, Route} from 'react-router-dom'
import Gastos from './components/gastos/Gastos';
import Entradas from './components/entradas/Entradas';
import GastosMes from './components/gastos/GastosMes';
 



function App() {
  return (
    
    <div >
      <BrowserRouter>
      <h1>Projeto F</h1>
      <ul>
        <li><Link to="">Tela inicial</Link></li>      
        <li><Link to="/telaDeCadastro">Gastos</Link>
        <li><Link to="/gastosMensais">Gastos por mes</Link></li>
        </li>
        <li><Link to="/telaDeEntradas">Entradas</Link></li>
    </ul>
    <Routes>
      <Route path='/telaDeCadastro' element={<Gastos/>}></Route>
      <Route path='/telaDeEntradas' element={<Entradas/>}></Route>
      <Route path='/gastosMensais' element={<GastosMes/>}></Route>
    </Routes>
    </BrowserRouter>
    </div>
    
  );
}

export default App;
