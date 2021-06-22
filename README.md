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
Importar arquivo json do insomnia no diretório /insomnia

## Recursos disponíveis
Existem os seguintes recursos que poderão ser acessados pelos métodos GET, POST, PUT e DELETE:<br>
#### Médicos (users) - /doctors

## Formato dos Dados
Todos os dados enviados e recebidos pela API estão em JSON (application/json).

## POST Cadastrar Médico
Parâmetros | Descrição
:-------   | :------
name       | Nome completo do Médico  - [Máximo de 120 caracteres]
crm        | CRM do médico -            [Números de no máximo 7 dígitos]
phone      | Telefone -                 [Números de no máximo 12 dígitos]
cellPhone  | Celular -                  [Números de no máximo 12 dígitos]
cep        | Cep -                      [Números de no máximo 8 dígitos]
specialities | Especialidades -         [Array com id de pelo menos duas especialidades]
