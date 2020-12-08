package miniScheme;

import java.util.List;

@FunctionalInterface
public interface Procedure {
    Object call(final List<Object> args);
}
