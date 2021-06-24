# API Gerenciamento de Médicos
API RESTful para uma plataforma simples de gerenciamento de médicos.<br>
Banco de dados H2 foi utilizado para testes 

## Endpoint
O endpoint de conexão com a API REST está no endereço: http://localhost:8080

## Execução
```bash
git clone https://github.com/fmrenan/api-gerenciamentoImoveis.git
```
Importar como projeto maven no SpringTool Suite e executar.

## Realizar Requisições
Importar arquivo json do insomnia no diretório [/insomnia](https://github.com/fmrenan/api-doctorsmanagement/blob/main/insomnia/Insomnia_2021-06-22.json)

## Recursos disponíveis
Existem os seguintes recursos que poderão ser acessados pelos métodos GET, POST, PUT e DELETE:<br>
#### Médicos (doctors) - `/doctors`

## Formato dos Dados
Todos os dados enviados e recebidos pela API estão em JSON (application/json).

## GET Listar Médicos
`/doctors`

## GET Listar um Médico
`/doctors/{id}`

## GET Buscar um Médico por atributos - Todos atributos são opcionais
```bash
/doctors?name=Nome&crm=CRM&phone=TELEFONE&cellPhone=CELULAR&cep=CEP&specialty=Nome da especialidade
```

## POST Cadastrar Médico
`/doctors`
##### Corpo da requisição: 
Parâmetros | Descrição
:-------   | :------
name       | Nome completo do Médico  - [Máximo de 120 caracteres]
crm        | CRM do médico -            [Números de no máximo 7 dígitos]
phone      | Telefone -                 [Números de no máximo 12 dígitos]
cellPhone  | Celular -                  [Números de no máximo 12 dígitos]
cep        | Cep -                      [Números de no máximo 8 dígitos]
specialties | Especialidades -         [Array com id de pelo menos duas especialidades]

## PUT Atualizar Médico
`/doctors/{id}`
##### Corpo da requisição: 
Parâmetros | Descrição
:-------   | :------
name       | Nome completo do Médico  - [Máximo de 120 caracteres]
crm        | CRM do médico -            [Números de no máximo 7 dígitos]
phone      | Telefone -                 [Números de no máximo 12 dígitos]
cellPhone  | Celular -                  [Números de no máximo 12 dígitos]
cep        | Cep -                      [Números de no máximo 8 dígitos]
specialties | Especialidades -         [Array com id de pelo menos duas especialidades]

## DELETE Excluir um Médico - Soft Delete
`/doctors/{id}`

## Possíveis Exceções: 
#### 422: Unprocessable Entity
`Error: Argument Exception` Motivo: Algum dos campos não atende às validações necessárias<br>
`Error: Invalid Address` Motivo: CEP inválido

#### 404: Not Found
`Error: Address not found` Motivo: CEP não encontrado<br>
`Error: Resource not found` Motivo: Recurso não encontrado com Id informado
