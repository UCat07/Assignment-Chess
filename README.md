Starting File is Main.java .

Save and load function at GameLogic.java .

放兵在Board.java .

Update Board.java
- delete xor in initializePiece
- add toggleTorXor to switch pieces after every two turns

Update GameLogic.java
- edit switchTurn so that correct turn correctly and handle when to switch pieces
- add handlePieceAlternation to make sure while switching pieces the pieces to switch will switch properly

Update BoardPanel.java
- not using text anymore, use png to represent pieces
