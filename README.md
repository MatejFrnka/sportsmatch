## Development rules:

- Use `Lombok`
- Only Use Data Transfer Objects (Dto's) for request/response data (e.g. create a `dtos` package (do not put this package into the `models` package).
- Make *everything* configurable (e.g. via values in `.env` and `application.properties`), i.e. no constant value should be hard-coded. The database credentials are already pre-configured this way.
- Unit tests should be a part of pull requests
- Use the object wrapper for primitive types, e.g. `Long` instead of `long`
- Naming
    - Entities/Models
        - Use camelCase for Java properties and snake_case for the corresponding database column names. For example `private String activationToken` in the User model should be mapped to the `activation_token` column in the `users` table
    - Database:
        - Use plurals for database tables, e.g. `users`, `posts`, `likes` (and singular for the corresponding models names, i.e. `User`, `Post`, `Like`)
        - Use `@GeneratedValue(strategy = GenerationType.IDENTITY)` for auto-incremented fields
    - Tests: use descriptive (test) method names (to improve readability):
        - : `canCreateModel()`
        - : `isEmptyIsFalseForReceiptWithItems()`
        - : `canAddPermissionsToUsers()`
    - Endpoints: always start with `/api/v1/`
    - Endpoints: use all lowercase letters and '-' for spaces
        - : `/api/v1/user/vouchers`
        - : `/api/v1/forgot-password`
    - Create descriptive branch names, e.g. feature-user-registration
- Use `this` keyword only to avoid variable name conflicts
- Use the [code formatting](https://blog.jetbrains.com/idea/2020/06/code-formatting/) feature in Intellij (CTRL+ALT+L / ⌥⌘L)
- Have at least 90% test coverage regarding services (unit test) and controllers (integration tests)
- Use [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
    - Have a look [here](https://stackoverflow.com/questions/42979700/how-to-configure-google-java-code-formatter-in-intellij-idea-17) on how to configure the google java style guides in IntelliJ
    - Make sure to use braces {} with `if`, `else`, `for`, `do` and `while` statements, even when the body is empty or contains only a single statement.


## Processes:
- Push only when *all* tests and style checks have passed
- Make sure there are no unresolved conflicts esp. in other than .java files
- see [CONTRIBUTING](CONTRIBUTING.md)

## Useful links

Contributing:

- see [CONTRIBUTING](CONTRIBUTING.md)

Commit messages:

- https://chris.beams.io/posts/git-commit/

Git cheat sheet

https://docs.google.com/spreadsheets/d/1Y6ylJLSbkUqLzn9kN_rQSgDlssRpR-ZFresF46apFWY/edit?usp=sharing

## Git Workflow

See [CONTRIBUTING](CONTRIBUTING.md)
