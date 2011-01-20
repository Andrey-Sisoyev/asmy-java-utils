package home.patterns.test;

import home.log.WithLog;
import home.utils.reflection.ObjectsHierarchy;

public class TestCase<T> extends WithLog {
    public enum ExpectedTestResult {
        OK, SLOW, TIMEOUT, EXCEPTION, ERROR
    }
    private Long id = null;
    private T testObject = null;
    private ExpectedTestResult expectedResult = null;
    private String description = null;
    private String comments = null;
    private String testContext = null;

    public TestCase(T testObject) {
        super(new ObjectsHierarchy(TestCase.class, testObject.getClass()));
        this.testObject = testObject;
    }

    public TestCase(T testObject, ExpectedTestResult expectedResult) {
        super(new ObjectsHierarchy(TestCase.class, testObject.getClass()));
        this.testObject = testObject;
        this.expectedResult = expectedResult;
    }

    public TestCase(Long id, T testObject) {
        super(new ObjectsHierarchy(TestCase.class, testObject.getClass()));
        this.id = id;
        this.testObject = testObject;
    }

    public TestCase(Long id, T testObject, ExpectedTestResult expectedResult) {
        super(new ObjectsHierarchy(TestCase.class, testObject.getClass()));
        this.id = id;
        this.testObject = testObject;
        this.expectedResult = expectedResult;
    }

    public TestCase(Long id, T testObject, ExpectedTestResult expectedResult, String description) {
        super(new ObjectsHierarchy(TestCase.class, testObject.getClass()));
        this.id = id;
        this.testObject = testObject;
        this.expectedResult = expectedResult;
        this.description = description;
    }

    public TestCase(T testObject, ExpectedTestResult expectedResult, String description) {
        super(new ObjectsHierarchy(TestCase.class, testObject.getClass()));
        this.testObject = testObject;
        this.expectedResult = expectedResult;
        this.description = description;
    }

    public String generateTCInfo(boolean includeTestObjToString) {
        String s = "--------\nClass: " + this.getTestObject().getClass().getName() + "\n";
        if(this.getId() != null)
            s += "ID: " + this.getId().toString() + "\n";
        if("".equals(this.getDescription()) && this.getDescription() != null)
            s += "Description: " + this.getDescription() + "\n";
        if(this.getExpectedResult() != null)
            s += "Expected: " + this.getExpectedResult().toString() + "\n";
        if(includeTestObjToString && this.getTestObject() != null)
            s += "TestObject: " + this.getTestObject().toString() + "\n";
        if("".equals(this.getComments()) && this.getComments() != null)
            s += "Comments: " + this.getComments() + "\n";
        if("".equals(this.getTestContext()) && this.getTestContext() != null)
            s += "TestContext: " + this.getTestContext() + "\n--------";
        return s;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpectedTestResult getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(ExpectedTestResult expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestContext() {
        return testContext;
    }

    public void setTestContext(String testContext) {
        this.testContext = testContext;
    }

    public T getTestObject() {
        return testObject;
    }

    public void setTestObject(T testObject) {
        this.testObject = testObject;
    }


}
