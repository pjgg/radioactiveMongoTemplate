
import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(features = Array(
  "src/test/resources/org/radioactiveMongoTemplate/crud/test/insert.feature",
  "src/test/resources/org/radioactiveMongoTemplate/crud/test/find.feature"
))
class RunCukesTest
