/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AllSwing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/*
 *
 * @author atfo
 */
public class Calculator extends javax.swing.JFrame {

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String nextStep2;
	private static String toReplace;
	private static String toReturn;
	private static int asterixAt;
	private static int xAt;

	private static int divideAt;
	private static int plusAt;
	private static int minusAt;
	private static char look;
	static String rootT = "\u221A";
	static char rootSymb = rootT.charAt(0);

	private static int beginningOfNumber(String inBrackets, int startSearch) {
		int looking = startSearch - 1;

		if (looking != 0 && (Character.isDigit(inBrackets.charAt(looking - 1)) || inBrackets.charAt(looking - 1) == '.' || inBrackets.charAt(looking - 1) == 'm')) {

			while (looking != 0 && (Character.isDigit(inBrackets.charAt(looking - 1))
					|| inBrackets.charAt(looking - 1) == '.' || inBrackets.charAt(looking - 1) == 'm')) {
				looking -= 1;
			}
		}

		return looking;
	}

	private static int[] Bubblesort(int[] numbers) {  //change the name everywhere its not bbublesort exactly anymore
		int size = numbers.length;
		boolean sorted=true;    //assume array is sorted
		for(int i=0;i<size-1;i++){      //check if its actually sorted //O(n) time complexity
			if(numbers[i]<=numbers[i+1]){}
			else{
				sorted=false;   //array is not sorted
				break;
			}
				
		}
		while (!sorted) {
			sorted = true;
			Arrays.sort(numbers); //O(nlogn) time complexity (double pivot quick sort)
		}
		return numbers;

	}

	private static int[] createPositiveArray(int ast, int x, int div) {

		int[] pos = new int[3];
		if (ast >= 0) {
			pos[0] = ast;
		}

		if (x >= 0) {
			pos[1] = x;
		}
		if (div >= 0) {
			pos[2] = div;
		}

		return pos;

	}

	private static int[] createPositiveArray2(int plus, int minus) {

		int[] pos = new int[2];
		if (plus >= 0) {
			pos[0] = plus;
		}

		if (minus >= 0) {
			pos[1] = minus;
		}

		return pos;

	}
	private static int endOfNumber(String inBrackets, int startSearch) {

		int looking = startSearch + 1;

		while (looking != inBrackets.length() - 1 && (Character.isDigit(inBrackets.charAt(looking + 1))
				|| inBrackets.charAt(looking + 1) == '.' || inBrackets.charAt(looking + 1) == 'm')) {

			looking++;

		}

		return looking;
	}

	public static BigInteger floorOfNthRoot(BigInteger x, int n) {
		int sign = x.signum();
		if (n <= 0 || (sign < 0))
			throw new IllegalArgumentException();
		if (sign == 0)
			return BigInteger.ZERO;
		if (n == 1)
			return x;
		BigInteger a;
		BigInteger bigN = BigInteger.valueOf(n);
		BigInteger bigNMinusOne = BigInteger.valueOf(n - 1);
		BigInteger b = BigInteger.ZERO.setBit(1 + x.bitLength() / n);
		do {
			a = b;
			b = a.multiply(bigNMinusOne).add(x.divide(a.pow(n - 1))).divide(bigN);
		} while (b.compareTo(a) == -1);
		return a;
	}

	private static double[] getValues(char symb, String initString) {
		String str1 = initString.substring(beginningOfNumber(initString, initString.indexOf(symb)),
				initString.indexOf(symb));

		str1 = simplify(str1);

		String str2 = initString.substring(initString.indexOf(symb) + 1,
				endOfNumber(initString, initString.indexOf(symb)) + 1);

		str2 = simplify(str2);

		double firstValue = Double.parseDouble(String.valueOf(str1));
		double secondValue = Double.parseDouble(String.valueOf(str2));
		double[] returning = new double[] { firstValue, secondValue };
		return returning;

	}

	private static String lookFor(int asterixAt, int xAt, int divideAt, String str) {

		int[] order = createPositiveArray(asterixAt, xAt, divideAt);

		order = BubbleSort(order);

		if (order[0] > 0) {
			return "" + str.charAt(order[0]);
		} else {
			if (order[1] > 0) {
				return "" + str.charAt(order[1]);
			} else {
				if (order[2] > 0) {
					return "" + str.charAt(order[2]);
				} else {
					return "";
				}
			}
		}
	}

	private static String lookFor2(int plusAt, int minusAt, String str) {

		int[] order = createPositiveArray2(plusAt, minusAt);

		order = BubbleSort(order);

		if (order[0] > 0) {
			return "" + str.charAt(order[0]);
		} else {
			if (order[1] > 0) {
				return "" + str.charAt(order[1]);
			} else {

				return "";

			}
		}
	}

	private static String lookForAdditionOrSubstraction(String toReturn) {
		
		while (toReturn.indexOf('+') >= 0 || toReturn.indexOf('-') >= 0) {

			plusAt = toReturn.indexOf('+');
			minusAt = toReturn.indexOf('-');

			String toLook = lookFor2(plusAt, minusAt, toReturn);
			

			look = toLook.charAt(0);

			if (look == '-') {

				String str1 = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('-')),
						toReturn.indexOf('-'));

				str1 = simplify(str1);

				String str2 = toReturn.substring(toReturn.indexOf('-') + 1,
						endOfNumber(toReturn, toReturn.indexOf('-')) + 1);

				str2 = simplify(str2);

				double firstValue = Double.parseDouble(String.valueOf(str1));
				double secondValue = Double.parseDouble(String.valueOf(str2));

				double preToReturn = firstValue - secondValue;

				String toReplace = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('-')),
						endOfNumber(toReturn, toReturn.indexOf('-')) + 1);

				toReturn = toReturn.replace(toReplace, (String.valueOf(preToReturn)).replace('-', 'm'));

			} else {
				if (look == '+') {

					String str1 = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('+')),
							toReturn.indexOf('+'));

					str1 = simplify(str1);

					String str2 = toReturn.substring(toReturn.indexOf('+') + 1,
							endOfNumber(toReturn, toReturn.indexOf('+')) + 1);

					str2 = simplify(str2);

					double firstValue = Double.parseDouble(String.valueOf(str1));
					double secondValue = Double.parseDouble(String.valueOf(str2));
					
					double preToReturn = firstValue + secondValue;
					
					String toReplace = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('+')),
							endOfNumber(toReturn, toReturn.indexOf('+')) + 1);

					String result = String.valueOf(preToReturn);
					toReturn = toReturn.replace(toReplace, result.replace('-', 'm'));

				}

			}
		}

		return toReturn;

	}
	
	private static String lookForBrackets(String initString){
		// TODO
		while(initString.contains("(")){
			int length = initString.length();
			ArrayList<Integer> opBrackets = new ArrayList<Integer>();
			for(int i = 0; i < length; i++){
				if(initString.charAt(i) == '('){
					opBrackets.add(i);
					//
				}else if(initString.charAt(i) == ')'){
					int last = opBrackets.size()-1;
					String current = initString.substring(opBrackets.get(last)+1, i);
					//
					//
					
					initString = initString.replace('('+current+')', lookForExponantiation(current));
					
					//
					opBrackets.remove(last);
					//
					break;
				}
			}	
		}
		
		
		return initString;
		
	}

	private static String lookForExponantiation(String initString) {
		
		if (initString.indexOf('^') >= 0 || initString.indexOf('!') >= 0 || initString.indexOf(rootSymb) >= 0) {
					while (initString.indexOf('^') >= 0 || initString.indexOf('!') >= 0 || initString.indexOf(rootSymb) >= 0) {
				int expAt = initString.indexOf('^');
				int rootAt = initString.indexOf(rootSymb);
				int factorAt = initString.indexOf('!');
		
				String toLook = lookFor(expAt, rootAt, factorAt, initString);
				look = toLook.charAt(0);
				double preToReturn;
				String replacer;
		
				if(look == '^' || look == rootSymb){
					double[] values = getValues(look, initString);
					double firstValue = values[0];
					double secondValue = values[1];
					if(look == '^'){
					preToReturn = Math.pow(firstValue, secondValue);
					replacer = ""+preToReturn;
					}else{
						replacer = ""+floorOfNthRoot(BigInteger.valueOf((int) secondValue),(int) firstValue);
					}
					toReplace = initString.substring(beginningOfNumber(initString, initString.indexOf(look)),
							endOfNumber(initString, initString.indexOf(look)) + 1);
				}else{
					String str1 = initString.substring(beginningOfNumber(initString, initString.indexOf('!')),
							initString.indexOf('!'));
					str1 = simplify(str1);
					double firstValue = Double.parseDouble(String.valueOf(str1));
					preToReturn = factorial(firstValue);
					replacer = ""+preToReturn;
					toReplace = initString.substring(beginningOfNumber(initString, initString.indexOf(look)), initString.indexOf(look)+1);
				}
				toReturn = initString.replace(toReplace, replacer);

				nextStep2 = lookForMultiplicationOrDivision(toReturn);

				initString = initString.replace(initString, nextStep2);

				initString = nextStep2;
				toReturn = initString;
			}

			return toReturn;
		} else {
			nextStep2 = lookForMultiplicationOrDivision(initString);
			initString = initString.replace(initString, nextStep2);
			initString = nextStep2;
			toReturn = initString;
			return toReturn;
		}
	}

	private static double factorial(double firstValue) {
		double result = 1;
		for(int i = 2; i < firstValue+1; i++){
			result *= i;
		}
		return result;
	}

	private static String lookForMultiplicationOrDivision(String toReturn) {
		if(toReturn.indexOf('*') >= 0 || toReturn.indexOf('X') >= 0 || toReturn.indexOf('/') >= 0) {
			while (toReturn.indexOf('*') >= 0 || toReturn.indexOf('X') >= 0 || toReturn.indexOf('/') >= 0) {
	
				asterixAt = toReturn.indexOf('*');
				xAt = toReturn.indexOf('X');
				divideAt = toReturn.indexOf('/');
	
				String toLook = lookFor(asterixAt, xAt, divideAt, toReturn);
				look = toLook.charAt(0);
				if (look == '*') {
					String str1 = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('*')),
							toReturn.indexOf('*'));
	
					str1 = simplify(str1);
	
					String str2 = toReturn.substring(toReturn.indexOf('*') + 1,
							endOfNumber(toReturn, toReturn.indexOf('*')) + 1);
	
					str2 = simplify(str2);
	
					double firstValue = Double.parseDouble(String.valueOf(str1));
					double secondValue = Double.parseDouble(String.valueOf(str2));
	
					double preToReturn = firstValue * secondValue;
	
					toReplace = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('*')),
							endOfNumber(toReturn, toReturn.indexOf('*')) + 1);
	
					String result = String.valueOf(preToReturn);
					toReturn = toReturn.replace(toReplace, result.replace('-', 'm'));
	
				} else {
					if (look == 'X') {
	
						String str1 = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('X')),
								toReturn.indexOf('X'));
	
						str1 = simplify(str1);
	
						String str2 = toReturn.substring(toReturn.indexOf('X') + 1,
								endOfNumber(toReturn, toReturn.indexOf('X')) + 1);
	
						str2 = simplify(str2);
	
						double firstValue = Double.parseDouble(String.valueOf(str1));
						double secondValue = Double.parseDouble(String.valueOf(str2));
	
						double preToReturn = firstValue * secondValue;
	
						toReplace = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('X')),
								endOfNumber(toReturn, toReturn.indexOf('X')) + 1);
	
						String result = String.valueOf(preToReturn);
						toReturn = toReturn.replace(toReplace, result.replace('-', 'm'));
	
					} else {
						if (look == '/') {
	
							String str1 = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('/')),
									toReturn.indexOf('/'));
	
							str1 = simplify(str1);
	
							String str2 = toReturn.substring(toReturn.indexOf('/') + 1,
									endOfNumber(toReturn, toReturn.indexOf('/')) + 1);
	
							str2 = simplify(str2);
	
							double firstValue = Double.parseDouble(String.valueOf(str1));
	
							double secondValue = Double.parseDouble(String.valueOf(str2));
	
							double preToReturn = firstValue / secondValue;
	
							toReplace = toReturn.substring(beginningOfNumber(toReturn, toReturn.indexOf('/')),
									endOfNumber(toReturn, toReturn.indexOf('/')) + 1);
							
							String result = String.valueOf(preToReturn);
							toReturn = toReturn.replace(toReplace, result.replace('-', 'm'));
	
						}
					}
				}
			}
		}
		nextStep2 = lookForAdditionOrSubstraction(toReturn);
		toReturn = nextStep2;

		return toReturn;
	}

	/*
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Calculator().setVisible(true);
			}
		});
	}

	private static double number_of_occurrences_of_char_in_string(String str, String letter) {

		int i = 0, count = 0;
		while ((i = str.indexOf(letter, i++)) != -1) {
			count++;
			i += letter.length();
		}

		return count;
	}

	private static String simplify(String str) {
		str = str.replace('m', '-');
		if (number_of_occurrences_of_char_in_string(str, ".") > 1) {
			int last = str.lastIndexOf('.');
			str = str.substring(0, last);

			return str;
		} else {
			return str;
		}

	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton AC;

	private javax.swing.JButton PI;

	private javax.swing.JButton clear;

	private javax.swing.JButton closeBracket;

	private javax.swing.JButton PPCM;

	private javax.swing.JButton decimal;

	private javax.swing.JTextField display;

	private javax.swing.JButton divide;

	private javax.swing.JButton equal;

	private javax.swing.JButton exponantiation;

	private javax.swing.JButton factorial;

	private javax.swing.JButton jButton1;

	private javax.swing.JButton jButton10;

	private javax.swing.JButton jButton11;

	private javax.swing.JButton jButton14;

	private javax.swing.JButton jButton16;

	private javax.swing.JButton jButton3;

	private javax.swing.JButton jButton4;

	private javax.swing.JButton jButton5;

	private javax.swing.JButton jButton7;

	private javax.swing.JButton jButton9;

	private javax.swing.JButton minus;

	private javax.swing.JButton multiply;

	private javax.swing.JButton openBracket;

	private javax.swing.JButton PGCD;

	private javax.swing.JLabel operationDisplay;

	private javax.swing.JButton plus;

	private javax.swing.JButton posneg;

	private javax.swing.JButton pourcent;

	private javax.swing.JButton root;
	// End of variables declaration//GEN-END:variables

	/*
	 * Creates new form CalculatorTest
	 */
	public Calculator() {
		setResizable(false);
		// TODO setPreferredSize(new Dimension(1000, 1000));
		initComponents();
	}

	private void ACActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ACActionPerformed
		
		display.setText("");
		operationDisplay.setText("");
	}// GEN-LAST:event_ACActionPerformed
	private void clearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearActionPerformed
		display.setText("");
	}// GEN-LAST:event_clearActionPerformed
	private void closeBracketActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeBracketActionPerformed

		// )
		display.setText(display.getText() + closeBracket.getText());
	}// GEN-LAST:event_closeBracketActionPerformed
	
	private void decimalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_decimalActionPerformed
		// .
		display.setText(display.getText() + decimal.getText());
	}// GEN-LAST:event_decimalActionPerformed
	private void divideActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_divideActionPerformed
		display.setText(display.getText() + divide.getText());

	}// GEN-LAST:event_divideActionPerformed
	private void equalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_equalActionPerformed
		calculate();
	}// GEN-LAST:event_equalActionPerformed
	private void exponantiationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exponantiationActionPerformed
		display.setText(display.getText() + exponantiation.getText());
	}// GEN-LAST:event_exponantiationActionPerformed
	private void factorialActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_factorialActionPerformed
		display.setText(display.getText() + "!");
	}// GEN-LAST:event_factorialActionPerformed
	/*
	 * This method is called from within the constructor to initialise the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	public void initComponents() {

		clear = new javax.swing.JButton();
		jButton14 = new javax.swing.JButton();
		decimal = new javax.swing.JButton();
		jButton16 = new javax.swing.JButton();
		equal = new javax.swing.JButton();
		divide = new javax.swing.JButton();
		posneg = new javax.swing.JButton();
		operationDisplay = new javax.swing.JLabel();
		openBracket = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		display = new javax.swing.JTextField();
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculate();
			}
		});
		
		jButton1 = new javax.swing.JButton();
		PI = new javax.swing.JButton();
		exponantiation = new javax.swing.JButton();
		closeBracket = new javax.swing.JButton();
		AC = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		plus = new javax.swing.JButton();
		jButton7 = new javax.swing.JButton();
		minus = new javax.swing.JButton();
		jButton9 = new javax.swing.JButton();
		jButton10 = new javax.swing.JButton();
		jButton11 = new javax.swing.JButton();
		multiply = new javax.swing.JButton();
		PGCD = new javax.swing.JButton();
		PPCM = new javax.swing.JButton();
		root = new javax.swing.JButton();
		pourcent = new javax.swing.JButton();
		factorial = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		clear.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		clear.setText("C");
		clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				clearActionPerformed(evt);
			}
		});

		jButton14.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton14.setText("0");
		jButton14.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton14ActionPerformed(evt);
			}
		});

		decimal.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		decimal.setText(".");
		decimal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				decimalActionPerformed(evt);
			}
		});

		jButton16.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton16.setText("7");
		jButton16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton16ActionPerformed(evt);
			}
		});

		equal.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		equal.setText("=");
		equal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				equalActionPerformed(evt);
			}
		});

		divide.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		divide.setText("/");
		divide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				divideActionPerformed(evt);
			}
		});

		posneg.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		posneg.setText("±");
		posneg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				posnegActionPerformed(evt);
			}
		});

		operationDisplay.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		operationDisplay.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		openBracket.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		openBracket.setText("(");
		openBracket.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openBracketActionPerformed(evt);
			}
		});

		jButton3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton3.setText("5");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		display.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		display.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton1.setText("4");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		PI.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		PI.setText("PI");
		PI.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PIActionPerformed(evt);
			}
		});

		exponantiation.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		exponantiation.setText("^");
		exponantiation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exponantiationActionPerformed(evt);
			}
		});

		closeBracket.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		closeBracket.setText(")");
		closeBracket.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeBracketActionPerformed(evt);
			}
		});

		AC.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		AC.setText("AC");
		AC.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ACActionPerformed(evt);
			}
		});

		jButton4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton4.setText("2");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		jButton5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton5.setText("3");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		plus.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		plus.setText("+");
		plus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				plusActionPerformed(evt);
			}
		});

		jButton7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton7.setText("6");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});

		minus.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		minus.setText("-");
		minus.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				minusActionPerformed(evt);
			}
		});

		jButton9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton9.setText("1");
		jButton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton9ActionPerformed(evt);
			}
		});

		jButton10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton10.setText("8");
		jButton10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton10ActionPerformed(evt);
			}
		});

		jButton11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		jButton11.setText("9");
		jButton11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton11ActionPerformed(evt);
			}
		});

		multiply.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		multiply.setText("X");
		multiply.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				multiplyActionPerformed(evt);
			}
		});

		PGCD.setFont(new Font("Arial", Font.BOLD, 13)); // NOI18N
		PGCD.setText("PGCD");

		PPCM.setFont(new Font("Arial", Font.BOLD, 13)); // NOI18N
		PPCM.setText("PPCM");

		root.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		root.setText("\u221A");
		root.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rootActionPerformed(evt);
			}
		});

		pourcent.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		pourcent.setText("%");
		pourcent.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pourcentActionPerformed(evt);
			}
		});

		factorial.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
		factorial.setText("n!");
		factorial.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				factorialActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(factorial, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(pourcent, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(posneg, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(equal, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(operationDisplay, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.TRAILING)
									.addGroup(layout.createSequentialGroup()
										.addComponent(AC, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(clear, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
									.addGroup(layout.createSequentialGroup()
										.addComponent(PPCM, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(closeBracket, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
									.addGroup(layout.createSequentialGroup()
										.addComponent(PGCD, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(openBracket, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
									.addGroup(layout.createSequentialGroup()
										.addComponent(root, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(exponantiation, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
										.addComponent(jButton9, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(plus, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
									.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
											.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
											.addComponent(jButton16, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addGroup(layout.createSequentialGroup()
												.addComponent(jButton10, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(jButton11, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(multiply, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
											.addGroup(layout.createSequentialGroup()
												.addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(minus, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))))
									.addGroup(layout.createSequentialGroup()
										.addComponent(PI, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(jButton14, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(decimal, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(divide, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))))
						.addComponent(display, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE))
					.addGap(43))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(display, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(operationDisplay, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(plus, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton9, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(clear, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(AC, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(minus, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(PGCD, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(openBracket, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButton11, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton10, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(multiply, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(jButton16, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(PPCM, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(closeBracket, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButton14, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(decimal, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(divide, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(PI, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(exponantiation, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(root, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pourcent, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(posneg, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(equal, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(factorial, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(layout);

		pack();
	}
	private void calculate() {
			String str1 = display.getText();

			
			
			String received = str1.replace(" ", "");
			
			received = lookForNegation(received);

			String pourcent = lookForPourcents(received);

			String firstStep = lookForBrackets(pourcent);

			String secondStep = lookForExponantiation(firstStep);
			operationDisplay.setText(simplify(secondStep));
	}

	private String lookForNegation(String received) {
		StringBuilder builder = new StringBuilder(received);
		if(received.charAt(0) == '-'){
			builder.setCharAt(0, 'm');
		}
		for(int i = 0; i < received.length(); i++){
			if(received.charAt(i) == '-' && received.charAt(i-1) == '('){
				builder.setCharAt(i, 'm');
				builder.deleteCharAt(i-1);
				builder.deleteCharAt(endOfNumber(received, i));
			}
		}
		received = builder.toString();
		
		return received;
	}

	private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton10ActionPerformed
		// 8
		display.setText(display.getText() + jButton10.getText());
	}// GEN-LAST:event_jButton10ActionPerformed
	private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton11ActionPerformed
		// 9
		display.setText(display.getText() + jButton11.getText());
	}// GEN-LAST:event_jButton11ActionPerformed
	private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton14ActionPerformed
		// 0
		display.setText(display.getText() + jButton14.getText());
	}// GEN-LAST:event_jButton14ActionPerformed
	private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton16ActionPerformed
		// 7
		display.setText(display.getText() + jButton16.getText());
	}// GEN-LAST:event_jButton16ActionPerformed
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// 4
		display.setText(display.getText() + jButton1.getText());
	}// GEN-LAST:event_jButton1ActionPerformed
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		// 5
		display.setText(display.getText() + jButton3.getText());
	}// GEN-LAST:event_jButton3ActionPerformed
	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		// 2
		display.setText(display.getText() + jButton4.getText());
	}// GEN-LAST:event_jButton4ActionPerformed
	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		// 3
		display.setText(display.getText() + jButton5.getText());
	}// GEN-LAST:event_jButton5ActionPerformed
	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
		// 6
		display.setText(display.getText() + jButton7.getText());
	}// GEN-LAST:event_jButton7ActionPerformed
	private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
		// 1
		display.setText(display.getText() + jButton9.getText());
	}// GEN-LAST:event_jButton9ActionPerformed
	private String lookForPourcents(String initString) {
		if (initString.indexOf('%') > 0) {
			while (initString.indexOf('%') > 0) {

				String toPourcent = initString.substring(beginningOfNumber(initString, initString.indexOf('%')),
						initString.indexOf('%'));

				double value = Double.parseDouble(toPourcent);
				double preToReturn = value / 100;

				toReplace = initString.substring(beginningOfNumber(initString, initString.indexOf('%')),
						initString.indexOf('%') + 1);

				String result = String.valueOf(preToReturn);
				toReturn = initString.replace(toReplace, result.replace('-', 'm'));

				initString = toReturn;
			}
			return toReturn;
		} else {
			return initString;
		}

	}
	
	private void minusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_minusActionPerformed
		display.setText(display.getText() + minus.getText());
	}// GEN-LAST:event_minusActionPerformed
	private void multiplyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_multiplyActionPerformed
		display.setText(display.getText() + multiply.getText());
	}// GEN-LAST:event_multiplyActionPerformed
	private void openBracketActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_openBracketActionPerformed
		// (
		display.setText(display.getText() + openBracket.getText());
	}// GEN-LAST:event_openBracketActionPerformed
	private void PIActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PIActionPerformed
		display.setText(display.getText() + (Math.PI));
	}// GEN-LAST:event_PIActionPerformed
	private void plusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_plusActionPerformed
		display.setText(display.getText() + plus.getText());
	}// GEN-LAST:event_plusActionPerformed
	private void posnegActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_posnegActionPerformed
		display.setText(display.getText() + "-");
	}// GEN-LAST:event_posnegActionPerformed
	private void pourcentActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_pourcentActionPerformed
		display.setText(display.getText() + pourcent.getText());
	}// GEN-LAST:event_pourcentActionPerformed
	private void rootActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rootActionPerformed
		display.setText(display.getText() + root.getText());
	}// GEN-LAST:event_rootActionPerformed

}
