package binarek.robio.ftl.adapter.persistence

import binarek.robio.shared.model.CallContext
import binarek.robio.shared.model.CorrelationId
import binarek.robio.shared.model.User

final class CallContextFixture {

    static CallContext callContext() {
        CallContext.of(
                User.of('testuser'),
                '/test/process',
                CorrelationId.of('correlation-id'),
        )
    }
}
