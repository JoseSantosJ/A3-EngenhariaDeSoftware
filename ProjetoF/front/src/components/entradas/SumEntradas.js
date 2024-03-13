import React, { useEffect, useState } from 'react';

function SumEntradas() {
  const [sumEntrada, setSumEntrada] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/somadasentradas")
      .then(retorno => retorno.json())
      .then(retorno_convertido => setSumEntrada(retorno_convertido));
  }, []);

  const valorEntrada = parseFloat(sumEntrada);

  return (valorEntrada.toFixed(2)
  );
}

export default SumEntradas;
