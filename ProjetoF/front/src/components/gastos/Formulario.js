// a function formulario esta recebendo botao
function Formulario({tparcelamento, tcompra, parcelamento, botao, eventoTeclado, cadastrar, obj, cancelar, remover, alterar, Fontes, fonteSelecionada, setFonteSelecionada, aoSelecionarFonte}){

    return(
        
        <form>
            <div>
            <input type='button' value="parcelado" onClick={tparcelamento} className="btn btn-secondary"/>
            <input type='button' value="avista" onClick={tcompra} className="btn btn-secondary"/>

                 
            </div>
            {
                parcelamento
                ?
                <><input type='text' value={obj.datac} onChange={eventoTeclado} name='datac' placeholder="datac" className="form-control" />
                <input type='text' value={obj.valordp} onChange={eventoTeclado} name='valordp' placeholder="valordp" className="form-control" />
                <input type="text" value={obj.ndp} onChange={eventoTeclado} name='ndp' placeholder="ndp" className="form-control" />
                <input type='text' value={obj.motivo} onChange={eventoTeclado} name='motivo' placeholder="motivo" className="form-control" />
                <input type='text' value={obj.valor} onChange={eventoTeclado} name='valor' placeholder="valor" className="form-control" />
                
                </>
                :
                <>
                <input type='text' value={obj.data} onChange={eventoTeclado} name='data' placeholder="data" className="form-control" />
                <input type='text' value={obj.motivo} onChange={eventoTeclado} name='motivo' placeholder="motivo" className="form-control" />
                <input type='text' value={obj.valor} onChange={eventoTeclado} name='valor' placeholder="valor" className="form-control" />
                <select value={obj.codigofonte} onChange={aoSelecionarFonte} name="codigofonte" className="form-control">
  <option value="">Selecione uma Fonte</option>
  {Fontes.map((fonte) => (
    <option key={fonte.codigofonte} value={fonte.codigofonte}>
      {fonte.nomefonte}
    </option>
  ))}
</select>
                </>
               

            }

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