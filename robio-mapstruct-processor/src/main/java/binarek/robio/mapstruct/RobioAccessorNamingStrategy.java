package binarek.robio.mapstruct;

import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.spi.util.IntrospectorUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class RobioAccessorNamingStrategy extends ImmutablesAccessorNamingStrategy {

    private static final String JOOQ_RECORDS_PACKAGE = "binarek.robio.db.tables.records";

    @Override
    public boolean isSetterMethod(ExecutableElement method) {
        if (belongsToJooqRecord(method)) {
            String methodName = method.getSimpleName().toString();
            return methodName.startsWith("set") && methodName.length() > 3;
        }
        return super.isSetterMethod(method);
    }

    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        if (belongsToJooqRecord(getterOrSetterMethod)) {
            String methodName = getterOrSetterMethod.getSimpleName().toString();
            return IntrospectorUtils.decapitalize(methodName.substring(methodName.startsWith("is") ? 2 : 3));
        }
        return super.getPropertyName(getterOrSetterMethod);
    }

    private static boolean belongsToJooqRecord(ExecutableElement method) {
        var className = ((TypeElement) method.getEnclosingElement()).getQualifiedName().toString();
        return className.startsWith(JOOQ_RECORDS_PACKAGE + '.');
    }
}
