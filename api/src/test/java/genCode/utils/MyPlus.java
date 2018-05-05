package genCode.utils;//package unitTest;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import cn.crap.utils.MyString;
//import org.apache.poi.util.StringUtil;
//import org.mybatis.generator.api.CommentGenerator;
//import org.mybatis.generator.api.IntrospectedColumn;
//import org.mybatis.generator.api.IntrospectedTable;
//import org.mybatis.generator.api.Plugin;
//import org.mybatis.generator.api.Plugin.ModelClassType;
//import org.mybatis.generator.api.PluginAdapter;
//import org.mybatis.generator.api.dom.java.Field;
//import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
//import org.mybatis.generator.api.dom.java.InnerClass;
//import org.mybatis.generator.api.dom.java.Interface;
//import org.mybatis.generator.api.dom.java.JavaVisibility;
//import org.mybatis.generator.api.dom.java.Method;
//import org.mybatis.generator.api.dom.java.Parameter;
//import org.mybatis.generator.api.dom.java.TopLevelClass;
//import org.mybatis.generator.api.dom.xml.Attribute;
//import org.mybatis.generator.api.dom.xml.TextElement;
//import org.mybatis.generator.api.dom.xml.XmlElement;
//import org.mybatis.generator.config.Context;
//import org.mybatis.generator.config.JDBCConnectionConfiguration;
//import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
//import org.mybatis.generator.config.TableConfiguration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class MyPlus extends PluginAdapter implements Plugin
//{
//  static Logger logger = LoggerFactory.getLogger(MyPlus.class);
//
//  public boolean validate(List<String> warnings) {
//    return true;
//  }
//
//  public void initialized(IntrospectedTable introspectedTable)
//  {
//    super.initialized(introspectedTable);
//  }
//
//  /**
//   * 为model添加序列化
//   * @param clazz
//   */
//  private void addSerialVersionUID(InnerClass clazz)
//  {
//    Field field = new Field();
//    field.setVisibility(JavaVisibility.PRIVATE);
//    field.setStatic(true);
//    field.setFinal(true);
//    field.setName("serialVersionUID");
//    field.setType(new FullyQualifiedJavaType("long"));
//    field.setInitializationString("1L");
//    List fields = clazz.getFields();
//    for (int i = 0; i < fields.size(); i++) {
//      Field currField = (Field)fields.get(i);
//      fields.set(i, field);
//      field = currField;
//    }
//    fields.add(field);
//  }
//
//  @Override
//  public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
//  {
//    addSerialVersionUID(topLevelClass);
//    return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
//  }
//
//
//  /**
//   * 分页
//   * @param element
//   * @param introspectedTable
//   * @return
//   */
//  public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable)
//  {
//    XmlElement choose = new XmlElement("choose");
//    XmlElement rangeLimitWhen = new XmlElement("when");
//    rangeLimitWhen.addAttribute(new Attribute("test", "limitStart != -1 and limitEnd != -1"));
//    rangeLimitWhen.addElement(new TextElement("limit ${limitStart} , ${limitEnd}"));
//    XmlElement limitStartWhen = new XmlElement("when");
//    limitStartWhen.addAttribute(new Attribute("test", "limitStart != -1"));
//    limitStartWhen.addElement(new TextElement("limit ${limitStart}"));
//    choose.addElement(rangeLimitWhen);
//    choose.addElement(limitStartWhen);
//    element.addElement(choose);
//    return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
//  }
//
//  /**
//   * 分页
//   * @param topLevelClass
//   * @param introspectedTable
//   * @return
//   */
//  public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
//  {
////    FullyQualifiedJavaType ptype = new FullyQualifiedJavaType("com.xxx.common.base.bean.BaseCriteria");
////    topLevelClass.addImportedType(ptype);
////    topLevelClass.addSuperInterface(ptype);
//    addSerialVersionUID(topLevelClass);
//    andGeneratedCriteriaImplSerialable(topLevelClass, "GeneratedCriteria");
//    andGeneratedCriteriaImplSerialable(topLevelClass, "Criterion");
//    andGeneratedCriteriaImplSerialable(topLevelClass, "Criteria");
//
//    addLimit(topLevelClass, introspectedTable, "limitStart");
//    addLimit(topLevelClass, introspectedTable, "limitEnd");
//
//    return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
//  }
//
//  private void andGeneratedCriteriaImplSerialable(TopLevelClass topLevelClass, String innerClassName) {
//    if (innerClassName == null) {
//      return;
//    }
//    InnerClass generatedCriteria = null;
//    for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
//      if (innerClassName.equals(innerClass.getType().getShortName())) {
//        generatedCriteria = innerClass;
//        break;
//      }
//    }
//    if (generatedCriteria == null) {
//      return;
//    }
//    FullyQualifiedJavaType ptype = new FullyQualifiedJavaType("java.io.Serializable");
//    topLevelClass.addImportedType(ptype);
//    generatedCriteria.addSuperInterface(ptype);
//    addSerialVersionUID(generatedCriteria);
//  }
//
//  private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name)
//  {
//    CommentGenerator commentGenerator = this.context.getCommentGenerator();
//    Field field = new Field();
//    field.setVisibility(JavaVisibility.PROTECTED);
//    field.setType(FullyQualifiedJavaType.getIntInstance());
//    field.setName(name);
//    field.setInitializationString("-1");
//    commentGenerator.addFieldComment(field, introspectedTable);
//
//    topLevelClass.addField(field);
//    char c = name.charAt(0);
//    String camel = Character.toUpperCase(c) + name.substring(1);
//    Method method = new Method();
//    method.setVisibility(JavaVisibility.PUBLIC);
//    method.setName("set" + camel);
//    method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
//    method.addBodyLine("this." + name + "=" + name + ";");
//    commentGenerator.addGeneralMethodComment(method, introspectedTable);
//
//    topLevelClass.addMethod(method);
//    method = new Method();
//    method.setVisibility(JavaVisibility.PUBLIC);
//    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
//    method.setName("get" + camel);
//    method.addBodyLine("return " + name + ";");
//    commentGenerator.addGeneralMethodComment(method, introspectedTable);
//    topLevelClass.addMethod(method);
//  }
//}