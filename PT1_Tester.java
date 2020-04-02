import java.io.IOException;
import java.util.List;

import acg.architecture.view.glyph.loader.GlyphLoader;
import acg.architecture.view.glyph.loader.EntryEdge;
import acg.architecture.view.glyph.loader.EntryCircle;
import acg.architecture.view.glyph.loader.LayoutBundle;
import acg.architecture.view.glyph.loader.InvalidLayoutException;


public class PT1_Tester {
    
    public static void main (String[] args) throws IOException, InvalidLayoutException {
        
        String fileName = "testFile.txt";
        
        GlyphLoader loader = new GlyphLoader(fileName);
        LayoutBundle bundle = loader.load();
        List<EntryCircle> circles = bundle.getCircles();
        List<List<EntryEdge>> edges = bundle.getEdgeLists();

        System.out.println("Circles");
        for (EntryCircle c : circles) {
            System.out.println(c);
        }//end for loop

        System.out.print("\n\n");

        System.out.println("Edges");
        for (int i = 0; i < edges.size(); i++) {
            System.out.println("polygon " + i);

            for (EntryEdge e : edges.get(i)) {
                System.out.println(e);
            }//end for loop
        }//end for loop 
    }//end method

}//end class