import React, { useEffect, useState } from 'react';

function SumGastos() {
  const [sumGasto, setSumGastos] = useState([]);

  useEffect(() => {
    fetch("https://a3-engenhariadesoftware.onrender.com/gastos/soma")
      .then(retorno => retorno.json())
      .then(retorno_convertido => setSumGastos(retorno_convertido));
  }, []);

  let valorGasto = parseFloat(sumGasto);
  valorGasto = isNaN(valorGasto) ? 0 : valorGasto.toFixed(2);
  return (valorGasto);
}

export default SumGastos;
