package pl.wasik.damian.project.beerwarehouse.controller.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.wasik.damian.project.beerwarehouse.controller.DashboardController;
import pl.wasik.damian.project.beerwarehouse.service.OrderService;
import pl.wasik.damian.project.beerwarehouse.service.ProductService;
import pl.wasik.damian.project.beerwarehouse.web.model.OrderDto;
import pl.wasik.damian.project.beerwarehouse.web.model.ProductDto;

import java.util.Arrays;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("DashboardController Unit Tests")
public class DashboardControllerUnitTest {
    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @Mock
    private Model model;

    @InjectMocks
    private DashboardController dashboardController;

    @Test
    @DisplayName("givenDashboardRequest_whenGetRequest_thenReturnsDashboardView")
    public void givenDashboardRequest_whenGetRequest_thenReturnsDashboardView() throws Exception {
        // Given
        MockitoAnnotations.openMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
        when(productService.findAll()).thenReturn(Arrays.asList(new ProductDto(), new ProductDto()));
        when(orderService.findAll()).thenReturn(Arrays.asList(new OrderDto(), new OrderDto()));

        // When & Then
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard/dashboard"));

        verify(productService, times(1)).findAll();
        verify(orderService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("products"), anyList());
        verify(model, times(1)).addAttribute(eq("orders"), anyList());
    }

    @Test
    @DisplayName("givenDashboardData_whenReadView_thenReturnCorrectViewName")
    public void givenDashboardData_whenReadView_thenReturnCorrectViewName() {
        // Given
        MockitoAnnotations.openMocks(this);
        when(productService.findAll()).thenReturn(Arrays.asList(new ProductDto(), new ProductDto()));
        when(orderService.findAll()).thenReturn(Arrays.asList(new OrderDto(), new OrderDto()));

        // When
        String viewName = dashboardController.readView(model);

        // Then
        Assertions.assertEquals("dashboard/dashboard", viewName);
        verify(productService, times(1)).findAll();
        verify(orderService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("products"), anyList());
        verify(model, times(1)).addAttribute(eq("orders"), anyList());
    }
}
