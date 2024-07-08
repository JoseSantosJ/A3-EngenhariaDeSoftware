# Controlador financeiro
![controlador](./assets/controlador.PNG?raw=true "controlador")

## Sobre o Projeto
O projeto Controlador financeiro tem o como objetivo Auxiliar no gerenciamento de receitas e despesas pessoais ajudando a visualizar quanto dinheiro foi gasto e ganho em determinados mes.somado a isso, também permite pre-visualizar quanto dinheiro ira ganhar ou gastar, ele separa o dinheiro gasto em compras avista ou no credito, possibilita cadastrar gastos parcelados calculando o valor das parcelas(ou valor total) atribuindo os dias de pagamento.

## Técnicas Utilizadas
Nesse projeto utilizei as seguintes técnicas

### modelgem de dados
relacionamento de entidades com many to one e one to many,


### MVC
```bash
associado com a estrutura de clean arquiteture utilizei o padrão MVC
tendo as pastas model e service Representando o M (responsavel pelos dados e o processamento de dados)
tendo a pasta Controller representado o C (responsavel pelas requisições e direcionamento )
E o V se encontra dentro da pasta controller(que apresenta os dados que o usurio usa/manipula)
```

 ## Features
 
 ### Tela de cadastro de gasto avista ou entrada
      a tela de cadastro de gasto a vista ou entrada tem os campos de data valor e motivo
  ![avista](./assets/Gastos.PNG?raw=true "avista")
      
 ### Tela de cadastro de gasto parcelado
      a tela de cadastro de gasto parcelado tem os campos de data da compra, valor da parcela, motivo e valor total.
      preenchendo um dos campos de valor o outro é calculado automaticamente após clicar no botão cadastrar 
 ![parceldo](./assets/Parcelado.PNG?raw=true "parceldo")

  ### Tela do GastoMes/EntraMes
      as telas de GastosMes e EntradaMes apresentam os gastos/receitas cadastradas naquele mês.
      apresentando tanto total de gasto/receitas do mês como a diferença das movimentas registradas no corrente mês
 ![gastosmes](./assets/GastosMes.PNG?raw=true "gastosmes")


# Instruções para utilização do aplicativo Web
 acesse o link https://fronta3.onrender.com
 
  ```bash
     atualmente back end necessita de 1-3 minutos para iniciar
 ```
