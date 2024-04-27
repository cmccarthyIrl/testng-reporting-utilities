package com.reports.utils.aspects;

import com.reports.utils.annotations.Action;
import com.reports.utils.annotations.Step;
import com.reports.utils.logging.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LoggingAspect class for aspect-oriented logging.
 */
@Aspect
@SuppressWarnings("unused")
public class LoggingAspect {

    private static LogManager logger = null;

    ///////////Action///////////

    /**
     * Pointcut for methods annotated with @Action.
     */
    @Pointcut("execution(@com.reports.utils.annotations.Action * *(..))")
    public void logAction() {
    }

    /**
     * Advice to log action descriptions.
     * @param joinPoint The join point.
     */
    @Before("logAction()")
    public void logActionDescription(JoinPoint joinPoint) {
        String description = getDescription(joinPoint);
        logger.info("Action::" + description);
    }

    ///////////Step///////////

    /**
     * Pointcut for methods annotated with @Step.
     */
    @Pointcut("execution(@com.reports.utils.annotations.Step * *(..))")
    public void logStep() {
    }

    /**
     * Advice to log step descriptions.
     * @param joinPoint The join point.
     */
    @Before("logStep()")
    public void logStepDescription(JoinPoint joinPoint) {
        String description = getDescription(joinPoint);
        logger.info("Step::" + description);
    }

    /**
     * Get the description from the annotation aspect.
     * @param joinPoint The join point.
     * @return The description.
     */
    private String getDescription(JoinPoint joinPoint) {
        if (joinPoint.getTarget() == null) {
            logger = new LogManager(LoggingAspect.class);
        } else {
            logger = new LogManager(joinPoint.getTarget().getClass());
        }

        //Get the description from the annotation aspect
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String description;
        if (method.getAnnotation(Action.class) != null) {
            description = method.getAnnotation(Action.class).value();
        } else {
            description = method.getAnnotation(Step.class).value();
        }

        //get the string values matching the regex pattern
        Pattern pattern = Pattern.compile("(\\Q{\\E\\d+\\Q}\\E)");
        Matcher matcher = pattern.matcher(description);
        HashMap<String, String> regexPatternMap = new HashMap<>();
        while (matcher.find()) {
            regexPatternMap.put(matcher.group(), "");
        }

        //filter the string values to match the regex pattern
        for (String regexPatternKey : regexPatternMap.keySet()) {
            int i = Integer.parseInt(regexPatternKey.replaceAll("[\\Q{}\\E]*", ""));
            if (i < joinPoint.getArgs().length) {
                regexPatternMap.put(regexPatternKey, joinPoint.getArgs()[i].toString());
            } else {
                regexPatternMap.put(regexPatternKey, regexPatternKey);
            }
        }

        //construct the description
        for (String regexPatternKey : regexPatternMap.keySet()) {
            description = description.replaceAll("\\Q" + regexPatternKey + "\\E", regexPatternMap.get(regexPatternKey));
        }

        return description;
    }
}