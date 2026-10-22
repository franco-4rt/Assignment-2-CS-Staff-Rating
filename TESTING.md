# Testing

## Approach: slice tests

For this project I chose to write **slice tests**. Each one tests a single layer of the app on its own:

| Slice | What it tests |
|---|---|
| `@WebMvcTest` | The controller (web layer) |
| `@DataJpaTest` | The repository and database (persistence layer) |
| Validation | The model's validation rules |

## Why slice tests

I chose slice tests because I think they help me better understand the structure and the processes involved in each component of this app.

In the future I may choose to write a full integration test, but for now I chose slice tests for the learning aspect.
