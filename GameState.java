// Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.List;

class GameState {
   private int turn;
   private List<Piece> pieces;

   public GameState(int var1, List<Piece> var2) {
      this.turn = var1;
      this.pieces = var2;
   }

   public int getTurn() {
      return this.turn;
   }

   public List<Piece> getPieces() {
      return this.pieces;
   }
}
