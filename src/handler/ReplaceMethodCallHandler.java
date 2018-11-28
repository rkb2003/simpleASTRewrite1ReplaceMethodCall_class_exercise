/**
 */
package handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import analysis.JavaASTAnalyzeReplaceMethodCall;
import view.ASTRewriteViewer;

/**
 * @since JavaSE-1.8
 */
public class ReplaceMethodCallHandler {
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   Shell shell;
   
   public String newMethod = null;
   public String toBeReplaced = null;

   @Execute
   public void execute(EPartService service) {
      // TODO Class Exercise
      InputDialog dlgMethodToBeReplaced = new InputDialog(shell, "", "Enter method name to be replaced: e.g 'classname:methodname'", "", null);
      if (dlgMethodToBeReplaced.open() == Window.OK) {
    	  toBeReplaced = dlgMethodToBeReplaced.getValue();
         System.out.println(dlgMethodToBeReplaced.getValue());
         
         InputDialog dlgNewMethodInfo = new InputDialog(shell, "", "Enter new method name : e.g 'packagename:classname:methodname'", "", null);
         if (dlgNewMethodInfo.open() == Window.OK) {
        	 newMethod = dlgNewMethodInfo.getValue();
        	       	
            System.out.println(dlgNewMethodInfo.getValue());
         }
      }

      MPart part = service.findPart(ASTRewriteViewer.VIEWID);
      if (part != null) {
         if (part.getObject() instanceof ASTRewriteViewer) {
            JavaASTAnalyzeReplaceMethodCall analyze = new JavaASTAnalyzeReplaceMethodCall( //
                  (ASTRewriteViewer) part.getObject(), toBeReplaced , newMethod);
            try {
               analyze.analyze();
            } catch (Exception e) {
               e.printStackTrace();
            }
         } else {
            System.out.println("[DBG] Please open the part descriptor view!!");
         }
      }
   }
}