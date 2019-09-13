
// Dsp-block async_set_register
// Description here
// Inititally written by dsp-blocks initmodule.sh, 20190913
package async_set_register

import chisel3._
import chisel3.experimental._
import chisel3.util._
import dsptools.{DspTester, DspTesterOptionsManager, DspTesterOptions}
class async_set_register(val n : Int=8 ) extends BlackBox(Map("n"->n)) with HasBlackBoxInline {
        val io = IO(new Bundle{
            val D = Input(UInt(n.W))
            val Q = Output(UInt(n.W))
            val clock = Input(Clock())
            val set = Input(Bool())
        }
    )

setInline("async_set_register.v",
    s"""
    |module async_set_register #( n=8 ) (
      |    input  [n-1:0] D,
      |    output  [n-1:0] Q,
      |    input clock,
      |    input set
      |);
      |always @(posedge clk or posedge set) begin
      |    if(set) begin
      |      Q <= n'h1;
      |    end else begin
      |      Q <= D;
      |    end
      |end
      |endmodule
    """.stripMargin)

}

class async_set_register_inst(val n : Int=8 ) extends Module {
        val io = IO(new Bundle { 
            val D = Input(UInt(n.W))
            val Q = Output(UInt(n.W))
            val clock = Input(Clock())
            val set = Input(Bool())
        }
    )

    val reg=Module(new async_set_register).io
    reg.D:=io.D
    io.Q:=reg.Q
    reg.clock:=clock
    reg.set:=io.set
}
//This gives you verilog
object async_set_register_inst extends App { 
    chisel3.Driver.execute(args, () => new async_set_register_inst(n=8)) 
}

//This is a simple unit tester for demonstration purposes
//class unit_tester(c: async_set_register_inst ) extends DspTester(c) {
////Tests are here 
//    poke(c.io.D, 5)
//    step(5)
//    fixTolLSBs.withValue(1) {
//        expect(c.io.Q, 5)
//    }
//}
//
//////This is the test driver 
//object unit_test extends App {
//    iotesters.Driver.execute(args, () => new async_set_register_inst(
//            n=8
//        )
//    ){
//            c=>new unit_tester(c)
//    }
//}
