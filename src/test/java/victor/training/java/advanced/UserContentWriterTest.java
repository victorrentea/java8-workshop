package victor.training.java.advanced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java.advanced.model.User;
import victor.training.java.advanced.repo.UserRepo;

import java.io.StringWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserContentWriterTest {
    @Mock
    UserRepo userRepo;
    @InjectMocks
    UserContentWriter target;
    @Test
    void writeUsers() {
        User user = new User().setUsername("jdoe").setFullName("John D.");
        when(userRepo.findAll()).thenReturn(List.of(user));
        StringWriter stringWriter = new StringWriter();

        target.writeUsers(stringWriter);

        assertThat(stringWriter.toString()).isEqualTo("""
                username;fullname
                jdoe;John D.
                """);
    }
}