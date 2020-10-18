module com.example {
    requires tornadofx;
    requires kotlin.reflect;
    requires kotlin.stdlib;

    exports com.example to javafx.graphics;
    exports com.example.view to tornadofx;
}
