package com.epam.mjc;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringTokenizer stringTokenizer = new StringTokenizer(signatureString,"(");
        StringSplitter stringSplitter = new StringSplitter();
        List <String> delimiter  = new ArrayList();
        delimiter.add(" ");
        delimiter.add(")");
        delimiter.add("(");

        List <String> methodDesc = stringSplitter.splitByDelimiters(stringTokenizer.nextToken(),delimiter);
        List <String> arguments = stringSplitter.splitByDelimiters(stringTokenizer.nextToken(),delimiter);
        return assignValues(methodDesc,arguments);
    }

    private MethodSignature assignValues (List <String> methodDesc, List <String> arguments ){
        List <MethodSignature.Argument> args = new ArrayList<>();
        for (int i=0; i<arguments.size()-1; i+=2){
            MethodSignature.Argument temp = new MethodSignature.Argument (arguments.get(i),arguments.get(i+1));
            args.add(temp);
        }

        MethodSignature result = new MethodSignature(methodDesc.get(methodDesc.size()-1),args);
        result.setReturnType(methodDesc.get(methodDesc.size()-2));
        result.setAccessModifier(methodDesc.size()>2 ? methodDesc.get(0):null);

        return result;
    }
}
