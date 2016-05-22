
import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(features = Array(
  "src/test/resources/org/radioactiveMongoTemplate/crud/test/insert.feature",
  "src/test/resources/org/radioactiveMongoTemplate/crud/test/find.feature",
  "src/test/resources/org/radioactiveMongoTemplate/crud/test/delete.feature",
  "src/test/resources/org/radioactiveMongoTemplate/crud/test/update.feature"
  //"src/test/resources/org/radioactiveMongoTemplate/crud/test/queries.feature"
))
class RunCukesTest
