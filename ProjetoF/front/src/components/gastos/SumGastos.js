import React, { useEffect, useState } from 'react';

function SumGastos() {
  const [sumGasto, setSumGastos] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/somadosgasto")
      .then(retorno => retorno.json())
      .then(retorno_convertido => setSumGastos(retorno_convertido));
  }, []);

  const valorGasto = parseFloat(sumGasto);

  return (valorGasto.toFixed(2) );
}

export default SumGastos;
