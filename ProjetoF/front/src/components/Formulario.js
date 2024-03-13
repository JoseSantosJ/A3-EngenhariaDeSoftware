// a function formulario esta recebendo botao
function Formulario({botao, eventoTeclado, cadastrar, obj, cancelar, remover, alterar}){
    
    return(
        
        <form>
            
              
                <input type='text' value={obj.data} onChange={eventoTeclado} name='data' placeholder="data" className="form-control" />
                <input type='text' value={obj.motivo} onChange={eventoTeclado} name='motivo' placeholder="motivo" className="form-control" />
                <input type='text' value={obj.valor} onChange={eventoTeclado} name='valor' placeholder="valor" className="form-control" />

            {
                botao
                ?
                //enquanto botao for verdadeiro exibira
                <input type='button' value="cadastrar" onClick={cadastrar} className="btn btn-primary"/>
                :
                //enquanto botao for falso exibira
                <div>
                    <input type='button' value="alterar" onClick={alterar} className="btn btn-warning"/>
                    <input type='button' value="Remover" onClick={remover} className="btn btn-danger"/>
                    <input type='button' value="Cancelar" onClick={cancelar} className="btn btn-secondary"/>
                </div>
            }
            
           
        </form>
    )
}


export default Formulario;