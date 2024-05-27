package pl.wasik.damian.project.beerwarehouse.controller.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import pl.wasik.damian.project.beerwarehouse.controller.DashboardController;
import pl.wasik.damian.project.beerwarehouse.service.OrderService;
import pl.wasik.damian.project.beerwarehouse.service.ProductService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DashboardController Integration Tests")
public class DashboardControllerMvcApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("givenDashboardRequest_whenGetRequest_thenReturnsDashboardView")
    public void givenDashboardRequest_whenGetRequest_thenReturnsDashboardView() throws Exception {
        // When & Then
        mockMvc.perform(get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard/dashboard"));
    }

    @Test
    @DisplayName("givenDashboardData_whenReadView_thenReturnCorrectViewName")
    public void givenDashboardData_whenReadView_thenReturnCorrectViewName() {
        // Given
        Model model = mock(Model.class);
        DashboardController dashboardController = new DashboardController(productService, orderService);

        // When
        String viewName = dashboardController.readView(model);

        // Then
        Assertions.assertEquals("dashboard/dashboard", viewName);
    }
}