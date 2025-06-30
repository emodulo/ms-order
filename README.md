# ms-order

O `ms-order` é um microserviço responsável por gerenciar pedidos de compra na plataforma ShopCar. Ele é projetado de forma genérica para processar pedidos de veículos, eletrodomésticos ou qualquer outro tipo de produto, mantendo um modelo desacoplado e seguro.

## Funcionalidades

- Cadastro de pedidos de compra
- Consulta de pedidos do usuário **autenticado**
- Listagem de pedidos
- Validação de permissões via Cognito
- Comunicação com outros serviços (ex: baixa de estoque via ms-vehicle)

## Arquitetura

Este serviço foi desenvolvido com:

- **Spring Boot** com arquitetura **Hexagonal (Ports and Adapters)**
- Banco de dados **PostgreSQL**
- Autenticação via **AWS Cognito** (JWT)
- Desacoplamento entre domínios (`customer`, `vehicle`, `order`)
- Implantação via **Kubernetes** com suporte a HPA, ConfigMap, Secrets, Ingress
- CI/CD com **GitHub Actions**

## Estrutura do Banco de Dados

Banco: `ordering` (PostgreSQL)

Tabela principal:

```sql
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    vehicle_id UUID NOT NULL,
    customer_name TEXT,
    customer_document TEXT,
    billing_address TEXT,
    shipping_address TEXT,
    product_description TEXT,
    quantity INTEGER,
    total_price DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

> Obs: O serviço é flexível e aceita dados do pedido no payload, sem necessidade de buscar dados em `ms-customer` ou `ms-vehicle`.

## Autenticação

O `ms-order` utiliza **AWS Cognito** como Identity Provider. A autenticação é feita via JWT (`Authorization: Bearer <token>`).

- Apenas usuários com grupo `buyer` podem criar pedidos.
- Ao consultar ou listar pedidos, o usuário visualiza apenas seus próprios registros com base no `sub` do token Cognito.

## Endpoints Disponíveis

| Método | Rota                            | Descrição                                      | Autenticação |
|--------|----------------------------------|-----------------------------------------------|--------|
| POST   | `/api/v1/orders`                | Cria um novo pedido                            | ✅      |
| GET    | `/api/v1/orders`                | Lista pedidos do cliente autenticado           | ✅      |
| GET    | `/api/v1/orders/{id}`           | Consulta um pedido por ID (se for do cliente)  | ✅      |

Para acessar a documentação da api localmente, acessar o endpoint:
http://localhost:8083/swagger-ui/index.html

## Kubernetes

O serviço está preparado para rodar em Kubernetes com os seguintes recursos:

- `Deployment` com readiness/liveness probe
- `Service` tipo `ClusterIP`
- `HorizontalPodAutoscaler` com 75% de CPU e memória
- `ConfigMap` para variáveis não sensíveis
- `Secret` para senhas e dados sigilosos
- `Ingress` configurado para roteamento HTTP
- Suporte a volumes persistentes no caso do banco de dados

## Como instalar via Docker (modo local)

> Repositório de infraestrutura: [`emodulo/shopcar-infra`](https://github.com/emodulo/shopcar-infra)

Execute o script PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File shopcar-via-docker.ps1
```

Este comando irá:

- Subir o PostgreSQL e os serviços em contêineres
- Criar a rede local
- Mapear as portas para acesso local

## Como instalar via Kubernetes (modo cluster)

> Repositório de infraestrutura: [`emodulo/shopcar-infra`](https://github.com/emodulo/shopcar-infra)

Execute o script PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File shopcar-via-kubernetes.ps1
```

Este comando irá:

- Aplicar os manifests no cluster Kubernetes (EKS, Minikube, etc)
- Criar os recursos: Deployment, Service, Ingress, ConfigMap, Secret, HPA
- Expor a aplicação via Ingress

---

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Kubernetes (YAML)
- AWS Cognito
- GitHub Actions (CI/CD)
- Docker & Docker Compose
- Helm (futuramente opcional)

---

## Estrutura do Projeto

```
ms-order/
├── domain/                # Lógica de negócio
├── application/           # Casos de uso
├── adapter-in/            # Controllers (REST)
├── adapter-out/           # JPA Repositories
├── port-in/               # Interfaces de acesso
├── port-out/              # Interfaces de saída
├── config/                # Segurança e configurações
└── kubernetes/            # Manifests YAML para deploy
```

---

## Contribuidores

- Kreverson Silva – Arquiteto e Desenvolvedor Principal