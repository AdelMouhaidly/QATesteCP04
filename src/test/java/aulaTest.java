import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class aulaTest {

    @Test
    public void loginTest(){

        //Dado: que esteja na página saucedemo.com
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/");
        assert driver.getTitle().equals("Swag Labs");

        //Quando: Inserido dados de usuário e senha válidos
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


        //Então: deverá ser redirecionado para a página https://www.saucedemo.com/inventory.html
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html");
        assert driver.findElement(By.id("shopping_cart_container")).isDisplayed();


    }

    @Test
    public void invalidLoginTest(){

        //Dado: que esteja na página saucedemo.com
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/");

        //Quando: Inserido dados de usuário e senha inválidos
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        //Então: deverá exibir mensagem de erro
        assert driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed();


    }

    @Test
    public void removeProductTest(){

        //Dado: que esteja logado e tenha produto no carrinho
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        //Quando: Remover o produto do carrinho
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();

        //Então: o produto deve ser removido e o botão volta para Add to cart
        assert driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).isDisplayed();


    }

    @Test
    public void addProductTest(){

        //Dado: que esteja logado e na página de produtos
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html");

        //Quando: Adicionar um produto ao carrinho
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        //Então: o carrinho deve mostrar 1 item
        assert driver.findElement(By.className("shopping_cart_badge")).isDisplayed();
        assert driver.findElement(By.id("remove-sauce-labs-backpack")).isDisplayed();


    }

    @Test
    public void cartTest(){

        //Dado: que esteja logado e tenha produtos no carrinho
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        //Quando: Acessar o carrinho
        driver.findElement(By.id("shopping_cart_container")).click();

        //Então: deve estar na página do carrinho com os produtos corretos
        assert driver.getCurrentUrl().equals("https://www.saucedemo.com/cart.html");
        assert driver.findElement(By.id("checkout")).isDisplayed();


    }

    @Test
    public void emptyLoginTest(){

        //Dado: que esteja na página saucedemo.com
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        //Quando: Tentar fazer login sem preencher os campos
        driver.findElement(By.id("login-button")).click();

        //Então: deve exibir mensagem de erro para campo obrigatório
        assert driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed();


    }

    @Test
    public void productDetailsTest(){

        //Dado: que esteja logado na página de produtos
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //Quando: Clicar em um produto para ver detalhes
        driver.findElement(By.id("item_4_title_link")).click();

        //Então: deve estar na página de detalhes do produto
        assert driver.getCurrentUrl().contains("/inventory-item.html");
        assert driver.findElement(By.id("back-to-products")).isDisplayed();


    }

}
