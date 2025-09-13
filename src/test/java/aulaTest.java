import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class aulaTest {

    @Test
    public void loginTest(){
        // Cenário de sucesso: Login com credenciais válidas
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/");
        assert driver.getTitle().equals("Swag Labs");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html");
        assert driver.findElement(By.id("shopping_cart_container")).isDisplayed();
    }

    @Test
    public void invalidLoginTest(){
        // Cenário de exceção: Login com credenciais inválidas
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        assert driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed();
    }

    @Test
    public void removeProductTest(){
        // Cenário de sucesso: Remover produto do carrinho
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        driver.findElement(By.id("remove-sauce-labs-backpack")).click();

        assert driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).isDisplayed();
    }

    @Test
    public void addProductTest(){
        // Cenário de sucesso: Adicionar produto ao carrinho
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        assert driver.findElement(By.className("shopping_cart_badge")).isDisplayed();
        assert driver.findElement(By.id("remove-sauce-labs-backpack")).isDisplayed();
    }

    @Test
    public void cartTest(){
        // Cenário de sucesso: Visualizar carrinho com produtos
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        driver.findElement(By.id("shopping_cart_container")).click();

        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/cart.html");
        assert driver.findElement(By.id("checkout")).isDisplayed();
    }

    @Test
    public void emptyLoginTest(){
        // Cenário de exceção: Login com campos vazios
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("login-button")).click();

        assert driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed();
    }

    @Test
    public void productDetailsTest(){
        // Cenário de sucesso: Visualizar detalhes do produto
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("item_4_title_link")).click();

        assert driver.getCurrentUrl().contains("/inventory-item.html");
        assert driver.findElement(By.id("back-to-products")).isDisplayed();
    }

    @Test
    public void emptyCartCheckoutTest(){
        // Cenário de exceção: Checkout com carrinho vazio
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        
        driver.findElement(By.id("shopping_cart_container")).click();
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/cart.html");
        
        assert driver.findElements(By.className("cart_item")).size() == 0;
        
        driver.findElement(By.id("checkout")).click();
        
        if(driver.getCurrentUrl().contains("checkout-step-one.html")) {
            driver.findElement(By.id("first-name")).sendKeys("Test");
            driver.findElement(By.id("last-name")).sendKeys("User");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            
            assert driver.findElements(By.className("cart_item")).size() == 0;
            assert driver.findElement(By.className("summary_total_label")).getText().equals("Total: $0.00");
        }
    }

}
