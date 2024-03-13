

function Tabela({vetor, selecionar}){
    return(
        <div className="tabel">
            <table className="tabela">
                {/* t + head = cabeçario da tabela/*/}
                <thead>
                    {/* tr = linha */}
                    <tr>
                        {/* dentro das linhas tem o th(th= coluna) */}
                        <th>#</th>
                        <th>Data</th>
                        <th>Motivo</th>
                        <th>valor</th>
                        <th>selecionar</th>
                    </tr>
                </thead>
                {/* t + body = corpo da tabela*/}
                <tbody>
                    {
                        vetor.map((obj, indice) => (
                        <tr key={indice}>
                            <td>{indice+1}</td>
                            <td>{obj.data}</td>
                            <td>{obj.motivo}</td>
                            <td>R$ {Number(obj.valor).toFixed(2)}</td>
                            <td><button onClick={() => {selecionar(indice)}} className="btn btn-success">selecionar</button></td>
                        </tr>
                    ))
                    }
                </tbody>
            </table>
        </div>
    )
}

export default Tabela;