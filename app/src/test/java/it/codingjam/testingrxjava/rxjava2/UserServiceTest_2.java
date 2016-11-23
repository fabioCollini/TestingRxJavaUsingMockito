package it.codingjam.testingrxjava.rxjava2;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class UserServiceTest_2 {

  @Rule public MockitoRule rule = MockitoJUnit.rule();

  @Mock StackOverflowService stackOverflowService;

  @InjectMocks UserService userService;

  @Test public void emptyTest() {
    userService.loadUsers();
  }
}