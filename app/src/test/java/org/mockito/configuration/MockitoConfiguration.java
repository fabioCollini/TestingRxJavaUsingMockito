package org.mockito.configuration;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoConfiguration extends DefaultMockitoConfiguration {
  public Answer<Object> getDefaultAnswer() {
    return new ReturnsEmptyValues() {
      @Override
      public Object answer(InvocationOnMock inv) {
        Class<?> type = inv.getMethod().getReturnType();
        if (type.isAssignableFrom(rx.Observable.class)) {
          return rx.Observable.error(createException(inv, "Observable"));
        } else if (type.isAssignableFrom(rx.Single.class)) {
          return rx.Single.error(createException(inv, "Single"));
        } else if (type.isAssignableFrom(Observable.class)) {
          return Observable.error(createException(inv, "Observable"));
        } else if (type.isAssignableFrom(Single.class)) {
          return Single.error(createException(inv, "Single"));
        } else {
          return super.answer(inv);
        }
      }
    };
  }

  @NonNull
  private RuntimeException createException(InvocationOnMock invocation, String className) {
    String s = invocation.toString();
    return new RuntimeException("No mock defined for invocation " + s +
        "\nwhen(" + s.substring(0, s.length() - 1) +
        ").thenReturn(" + className + ".just());");
  }
}