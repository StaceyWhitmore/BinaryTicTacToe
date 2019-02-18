# BinaryTicTacToe
I call this Binary Tic Tac Toe because it runs a bit-wise & comparison on the selected move against the possible win combinations
both result in a 1 then the move is a winning move. This same logic is also used for the AI which will make a move based on it's ability
to block it's opponent from making a move (similar to a greedy algorithm); however, at this point the AI makes this a priority over going
for an winning a move. So in future versions I will fix this defect in the AI but keep the current AI as an option for and "easy" mode.
```
var winsArray = [7, 56, 448, 73, 146, 292, 273, 84];//contains the vals for the 8 possible win combinations.

                                      = 84
                                    /
2^0 | 2^1| 2^2   =     1   | 2  | 4      = 7
____|____|____         ____|____|____
2^3 | 2^4| 2^5   =     8   | 16 | 32     = 56        
____|____|____         ____|____|____
2^6 | 2^7| 2^9   =     64  | 128| 448    = 448
                                     \
                                       = 273

top-left to bottom-right Diagonal        = 273
top-right to bottom-left Diagonal        = 84   
