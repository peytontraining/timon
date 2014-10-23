package vn.enclave.peyton.fusion;

import org.eclipse.core.expressions.PropertyTester;
public class PerspectivePropertyTester extends PropertyTester {

    @SuppressWarnings("static-access")
    @Override
    public boolean test(
        Object receiver, String property, Object[] args, Object expectedValue) {
        final ProjectPerspective perspective = (ProjectPerspective) receiver;
        System.out.println(perspective.ID.equals(expectedValue));
        return perspective.ID.equals(expectedValue);
    }

}
