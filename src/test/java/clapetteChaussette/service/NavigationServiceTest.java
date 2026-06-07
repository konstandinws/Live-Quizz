package clapetteChaussette.service;

import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.IDisplayable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NavigationServiceTest {

    @Mock
    Stage stage;
    @Mock
    IDisplayable displayable;

    @InjectMocks
    NavigationService navigationService;
    @Test
    void navigueto_history_notnulltest() throws Exception {
        Scene oldScene = mock(Scene.class);
        Scene newScene = mock(Scene.class);

        when(stage.getScene()).thenReturn(oldScene);
        when(displayable.initializeView()).thenReturn(newScene);

        navigationService.navigateTo(displayable, "Titre");

        verify(stage, times(1)).setScene(newScene);
        verify(stage, times(1)).setTitle("Titre");
        verify(stage, times(1)).show();
    }
    @Test
    void navigateTo_History_nulltest() throws Exception {
        Scene newScene = mock(Scene.class);

        when(stage.getScene()).thenReturn(null);
        when(displayable.initializeView()).thenReturn(newScene);

        navigationService.navigateTo(displayable, "Titre");

        verify(stage, times(1)).setScene(newScene);
        verify(stage, times(1)).setTitle("Titre");
        verify(stage, times(1)).show();
    }

    @Test
    void navigateTo_newscene() throws Exception {
        when(stage.getScene()).thenReturn(null);
        when(displayable.initializeView()).thenThrow(new Exception("Erreur vue"));
        assertThrows(RuntimeException.class, () -> {
            navigationService.navigateTo(displayable, "Titre");
        });

    }
    @Test
    void goBack_Scene() throws Exception {
        Scene oldScene = mock(Scene.class);
        Scene newScene = mock(Scene.class);
        when(stage.getScene()).thenReturn(oldScene);
        when(displayable.initializeView()).thenReturn(newScene);
        navigationService.navigateTo(displayable, "Titre");
        verify(stage, times(1)).setScene(newScene);
        navigationService.goBack();
        verify(stage, times(1)).setScene(oldScene);
    }
}
