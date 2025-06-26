package com.newgen.cbg.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.NoCustomContextException;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class ImageTagProcessor extends com.itextpdf.tool.xml.html.Image {
  public List<Element> end(WorkerContext ctx, Tag tag, List<Element> currentContent) {
    Map<String, String> attributes = tag.getAttributes();
    String src = attributes.get("src");
    List<Element> elements = new ArrayList<>(1);
    if (src != null && src.length() > 0) {
      Image img = null;
      if (src.startsWith("data:image/")) {
        String base64Data = src.substring(src.indexOf(",") + 1);
        try {
          img = Image.getInstance(Base64.decode(base64Data));
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (img != null)
          try {
            HtmlPipelineContext htmlPipelineContext = getHtmlPipelineContext(ctx);
            elements.add(getCssAppliers().apply((Element)new Chunk((Image)getCssAppliers().apply((Element)img, tag, htmlPipelineContext), 0.0F, 0.0F, true), tag, 
                  htmlPipelineContext));
          } catch (NoCustomContextException e) {
            throw new RuntimeWorkerException(e);
          }  
      } 
      if (img == null)
        elements = super.end(ctx, tag, currentContent); 
    } 
    return elements;
  }
}
