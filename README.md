# Analisador de Usinas RALIE

Sistema robusto para processamento e visualização de dados de usinas térmicas e renováveis, baseado no dataset RALIE (Relatório de Acompanhamento de Licenciamento de Empreendimentos) da ANEEL. O projeto foca em **alta performance**, processando mais de **400.000 registros** com eficiência.

## 🚀 Tecnologias Utilizadas

### Backend
* **Kotlin** com **Spring Boot 3**
* **Spring Data JPA** (Hibernate)
* **Kotlin Coroutines** (Processamento Assíncrono e Paralelo)
* **PostgreSQL** (Banco de dados relacional)
* **Apache Commons CSV** e **BOMInputStream** (Parsing de arquivos com encoding ISO-8859-1)

### Frontend
* **Angular 17+** (Standalone Components)
* **RxJS** (Estratégia reativa com Pipe Async)
* **HttpClient** (Consumo de API REST)

---

## 🛠️ Diferenciais Técnicos e Performance

Para lidar com o volume de dados massivo e os requisitos de unicidade, foram implementadas as seguintes estratégias:

1.  **Data Parallelism com Coroutines:** Utilização de `runBlocking` e `Dispatchers.IO` com `limitedParallelism` para processar o CSV em blocos (chunks). Isso permitiu que o parsing das strings e as validações ocorressem de forma concorrente, reduzindo drasticamente o tempo de carga.
2.  **Controle de Unicidade Thread-Safe:** Uso de `ConcurrentHashMap.newKeySet()` para garantir que, mesmo em um ambiente multi-thread, a validação de registros duplicados fosse atômica e performática antes da persistência.
3.  **Otimização de Escrita (JDBC Batching):** Configuração de `jdbc.batch_size` e `order_inserts` no Hibernate, transformando milhares de inserts individuais em operações de lote, minimizando o overhead de rede com o banco de dados.
4.  **Query de Ranking com Window Functions:** O Top 5 de usinas mais potentes utiliza SQL Nativo com a função `ROW_NUMBER() OVER (PARTITION BY u.ceg...)`. Isso garante que o resultado traga apenas uma linha por usina (a de maior potência), mesmo que existam múltiplos registros históricos no banco.
5.  **Pool de Conexões Ajustado:** Configuração refinada do **HikariCP** para suportar a carga paralela sem estourar o limite de conexões ou causar timeouts.

---

## 📋 Como Executar o Projeto

### Pré-requisitos
* Java 17+
* Node.js 18+
* PostgreSQL rodando (porta 5432)

