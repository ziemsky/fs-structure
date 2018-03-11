# Testing strategy
- Test as much as possible using frequently run, automated tests.
- Ensure completness of the tests by using coverage checks.
- Ensure quality of the tests by using mutation checks.
- Balance the effort required to maintain the tests against the value they provide by using various, complementing
  kinds of tests and by avoiding duplication of coverage where reasonable. 

In general, the approach used in this project is inspired by [Test Pyramid] concept, adapted to testing needs of a
microscopic-size framework rather than those of an application or a system that are typically subject of most of the
concept's descriptions.

## Kinds of Tests
The approach taken follows recognition that writing, maintaining and running tests is not free, and so the tests have
to strike balance between the effort invested in writing them and value they provide:
- Have dedicated, explicit tests that validate aspects or features that are most valuable, frequently used or whose
  impact is greatest when broken.
- Less risky aspects or features warrant less testing effort - in some cases you can skip having dedicated tests for
  these.
- Avoid explicitly testing the same thing multiple times - as long as a less risky aspect is implicitly covered by some
  tests, it may not require having its own dedicated tests.
- Avoid mocking file system, constructors or any static methods (typically JDK or third-party library methods used for
  interacting with the file system). If method under test interacts with any of these, your test should not be
  classified as a unit test.
- Higher-level tests (functional, integration) typically have higher maintenance and runtime costs so favour lower-level
  tests (unit). Keep higher-level tests focus on a minimal number of key happy paths and maybe one, critical error case
  to prove that its handling has been integrated into the whole stack, but leave exercising all the detailed edge cases
  to lower-level tests.
  
### Functional Tests
Tests interacting with the public interface of the library, that is with methods of class `FsStructure`.

Nothing is being mocked here and actual file system is being used.

### Integration Tests
Tests exercising methods of lower level classes that back/support the facade.

'Integration' is loosely interpreted here as testing 'one integration point' rather than the whole stack. Classes and
methods directly interacting with the file system are often not easy to mock, typically being static and coming from JDK
or from third party classes. Thus, tests exercising methods that interact with these, verify the integration of code
under test with third-party constructs.
 
### Unit Tests
Tests focusing on individual classes whose dependencies and collaborators are easy/ok to mock.

## Automated Test Quality Assurances
Coverage and mutation checks: initial assumption is to strive for 100% level of both but calculated cumulatively across
all types of checks. This means that it is acceptable for, say, unit tests not to satisfy 100% coverage on their own
because the rest will be satisfied by other tests.

This approach will be evaluated as the project progresses.       



[Test Pyramid]:                     https://martinfowler.com/articles/practical-test-pyramid.html