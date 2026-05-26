package org.project.expressart;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ExpressartApplicationTests {

    @Test
    void shouldVerifyApplicationPackageExists() {
        assertThat(ExpressartApplication.class).isNotNull();
    }
}
