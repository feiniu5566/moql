package org.datayoo.moql.parser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.lang.Validate;
import org.datayoo.moql.MoqlException;
import org.datayoo.moql.SelectorDefinition;
import org.datayoo.moql.antlr.FilterLexer;
import org.datayoo.moql.antlr.FilterParser;
import org.datayoo.moql.antlr.SelectorLexer;
import org.datayoo.moql.antlr.SelectorParser;
import org.datayoo.moql.metadata.ConditionMetadata;
import org.datayoo.moql.metadata.xml.FilterFormater;
import org.datayoo.moql.metadata.xml.SelectorFormater;
import org.datayoo.moql.util.StringFormater;
import org.datayoo.moql.xml.DefaultDocumentFormater;
import org.datayoo.moql.xml.XmlAccessException;

import java.io.ByteArrayInputStream;

/**
 * Created by tangtadin on 17/10/16.
 */
public abstract class MoqlParser {
  /**
   *
   * @param moql
   * @return SelectorMetadata or SetlectorMetadata
   */
  public static SelectorDefinition parseMoql(String moql) throws MoqlException {
    Validate.notEmpty(moql, "Parameter 'moql' is empty!");
    try {
      ANTLRInputStream is = new ANTLRInputStream(new ByteArrayInputStream(
          moql.getBytes()));
      SelectorLexer lexer = new SelectorLexer(is);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      SelectorParser parser = new SelectorParser(tokens);
      return parser.selector();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new MoqlException(StringFormater.format("Parse moql '{}' failed!",
          moql), e);
    }
  }

  public static ConditionMetadata parseCondition(String condition)
      throws MoqlException {
    Validate.notEmpty(condition, "Parameter 'condition' is empty!");
    try {
      ANTLRInputStream is = new ANTLRInputStream(new ByteArrayInputStream(
          condition.getBytes()));
      FilterLexer lexer = new FilterLexer(is);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      FilterParser parser = new FilterParser(tokens);
      return new ConditionMetadata(parser.searchCondition());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new MoqlException(StringFormater.format(
          "Parse condition '{}' failed!", condition), e);
    }
  }

  public static String translateMoql2Xml(String moql) throws MoqlException {
    SelectorDefinition definition = parseMoql(moql);
    return translateMetadata2Xml(definition);
  }

  public static String translateMetadata2Xml(SelectorDefinition definition)
      throws XmlAccessException {
    Validate.notNull(definition, "Parameter 'definition' is null!");
    DefaultDocumentFormater<SelectorDefinition> documentFormater = new DefaultDocumentFormater<SelectorDefinition>();
    SelectorFormater selectorFormater = new SelectorFormater();
    documentFormater.setFormater(selectorFormater);
    return documentFormater.exportObjectToString(definition);
  }


  public static SelectorDefinition translateXml2SelectorDefinition(
      String xmlData) throws MoqlException {
    Validate.notEmpty(xmlData, "xmlData is empty!");
    DefaultDocumentFormater<SelectorDefinition> documentFormater = new DefaultDocumentFormater<SelectorDefinition>();
    SelectorFormater selectorFormater = new SelectorFormater();
    documentFormater.setFormater(selectorFormater);
    return documentFormater.importObjectFromString(xmlData);
  }

  public static ConditionMetadata translateXml2ConditionMetadata(String xmlData)
      throws MoqlException {
    Validate.notEmpty(xmlData, "xmlData is empty!");
    DefaultDocumentFormater<ConditionMetadata> documentFormater = new DefaultDocumentFormater<ConditionMetadata>();
    FilterFormater filterFormater = new FilterFormater();
    documentFormater.setFormater(filterFormater);
    return documentFormater.importObjectFromString(xmlData);
  }

  public static String translateCondition2Xml(String condition)
      throws MoqlException {
    ConditionMetadata conditioneMetadata = MoqlParser.parseCondition(condition);
    return translateMetadata2Xml(conditioneMetadata);
  }

  public static String translateMetadata2Xml(
      ConditionMetadata conditionMetadata) throws XmlAccessException {
    Validate.notNull(conditionMetadata, "conditionMetadata is null!");
    DefaultDocumentFormater<ConditionMetadata> documentFormater = new DefaultDocumentFormater<ConditionMetadata>();
    FilterFormater filterFormater = new FilterFormater();
    documentFormater.setFormater(filterFormater);
    return documentFormater.exportObjectToString(conditionMetadata);
  }

}
