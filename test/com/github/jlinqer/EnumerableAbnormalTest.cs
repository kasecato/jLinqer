using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Com.JLinqer
{
    /// <summary>
    /// Created by Keisuke Kato
    /// </summary>
    [TestClass]
    public class EnumerableAbnormalTest
    {
        [TestMethod]
        public void Aggregate_abnormal()
        {
            // arrange
            List<int> list = new List<int>();

            // act and assert
            try
            {
                list.Aggregate(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                list.Aggregate((sum, elem) => sum + elem);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }
        }

        [TestMethod]
        public void All_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act and assert
            try
            {
                list.All(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Any_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act and assert
            try
            {
                list.Any(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Average_abnormal()
        {
            // arrange
            List<int> listInt = new List<int>() { 1, 2, 3, 4 };
            List<long> listLong = new List<long>() { 1, 2, 3, 4 };
            List<double> listDouble = new List<double>() { 1d, 2d, 3d, 4d };
            List<decimal> listBigDecimal = new List<decimal>() {
                new decimal(1d),
                new decimal(2d),
                new decimal(3d),
                new decimal(4d)
            };

            List<int> listIntEmpty = new List<int>();
            List<long> listLongEmpty = new List<long>();
            List<double> listDoubleEmpty = new List<double>();
            List<decimal> listBigDecimalEmpty = new List<decimal>();

            Func<int, int> funcInt = null;
            Func<long, int> funcLong = null;
            Func<double, int> funcDouble = null;
            Func<decimal, int> funcDecimal = null;

            // act and assert
            try
            {
                listInt.Average(funcInt);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
            try
            {
                listIntEmpty.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                listLong.Average(funcLong);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
            try
            {
                listLongEmpty.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                listDouble.Average(funcDouble);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
            try
            {
                listDoubleEmpty.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                listBigDecimal.Average(funcDecimal);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
            try
            {
                listBigDecimalEmpty.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }
        }

        [TestMethod]
        public void Average_overflow()
        {
            // arrange
            List<int> listOverflowInt = new List<int>() { int.MaxValue, 1 };
            List<int> listUnderflowInt = new List<int>() { int.MinValue, -1 };
            List<long> listOverflowLong = new List<long>() { long.MaxValue, 1 };
            List<long> listUnderflowLong = new List<long>() { long.MinValue, -1 };

            // act and assert
            try
            {
                listOverflowInt.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }
            try
            {
                listUnderflowInt.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }

            try
            {
                listOverflowLong.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }
            try
            {
                listUnderflowLong.Average(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }
        }

        [TestMethod]
        public void Concat_abnormal()
        {
            // arrange
            List<Object> list = new List<Object>();

            // act and assert
            try
            {
                list.Concat(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Count_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act and assert
            try
            {
                list.Count(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                list.LongCount(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void ElementAt_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act and assert
            try
            {
                list.ElementAt(-1);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentOutOfRangeException);
            }

            try
            {
                list.ElementAt(3);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentOutOfRangeException);
            }
        }

        [TestMethod]
        public void Except_abnormal()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };

            // act and assert
            try
            {
                first.Except(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void FirstOrDefault_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act and assert
            try
            {
                list.FirstOrDefault(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void First_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };
            List<int> listEmpty = new List<int>();

            // act and assert
            try
            {
                listEmpty.First();
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                list.First(x => x == 4);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }
        }

        [TestMethod]
        public void GroupBy_abnormal()
        {
            // arrange

            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 1),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
            };
            Func<Javascript, Javascript> func = null;

            // act and assert
            try
            {
                list.GroupBy(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void GroupJoin_abnormal()
        {
            // arrange
            List<int> outer = new List<int>() { 1, 2, 3 };
            List<int> inner = new List<int>() { 1, 2, 3 };
            Func<int, int> outerKey = x => x;
            Func<int, int> innerKey = y => y;
            Func<int, IEnumerable<int>, int> selector = (x, y) => x;
            Func<int, IEnumerable<int>, int> selectorNull = null;

            // act and assert
            try
            {
                outer.GroupJoin(null, outerKey, innerKey, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                outer.GroupJoin(inner, null, innerKey, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                outer.GroupJoin(inner, outerKey, null, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                outer.GroupJoin(inner, outerKey, innerKey, selectorNull);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Intersect_abnormal()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };

            // act and assert
            try
            {
                first.Intersect(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Join_abnormal()
        {
            // arrange
            List<int> outer = new List<int>() { 1, 2, 3 };
            List<int> inner = new List<int>() { 1, 2, 3 };
            Func<int, int> outerKey = x => x;
            Func<int, int> innerKey = y => y;
            Func<int, int, int> selector = (x, y) => x + y;
            Func<int, int, int> selectorNull = null;

            // act and assert
            try
            {
                outer.Join(null, outerKey, innerKey, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                outer.Join(inner, null, innerKey, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                outer.Join(inner, outerKey, null, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                outer.Join(inner, outerKey, innerKey, selectorNull);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Last_abnormal()
        {
            // arrange
            List<int> listEmpty = new List<int>();

            // act and assert
            try
            {
                listEmpty.Last();
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                listEmpty.Last(x => x == 0);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                listEmpty.Last(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Max_abnormal()
        {
            // arrange
            List<int> listInt = new List<int>();
            Func<int, int> func = null;

            // act and assert
            try
            {
                listInt.Max(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
            try
            {
                listInt.Max(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }
        }

        [TestMethod]
        public void Min_abnormal()
        {
            // arrange
            List<int> listInt = new List<int>();
            Func<int, int> func = null;

            // act and assert
            try
            {
                listInt.Min(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
            try
            {
                listInt.Min(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }
        }

        [TestMethod]
        public void OrderByDescending_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            Func<String, String> func = null;

            // act and assert
            try
            {
                list.OrderByDescending(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void OrderBy_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            Func<String, String> func = null;

            // act and assert
            try
            {
                list.OrderBy(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Range_abnormal()
        {
            // act and assert
            try
            {
                Enumerable.Range(0, -1);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentOutOfRangeException);
            }

            try
            {
                Enumerable.Range(2, int.MaxValue);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentOutOfRangeException);
            }
        }

        [TestMethod]
        public void Repeat_abnormal()
        {
            // act and assert
            try
            {
                Enumerable.Repeat("React", -1);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentOutOfRangeException);
            }
        }

        [TestMethod]
        public void Select_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };
            Func<int, bool> func = null;

            // act and assert
            try
            {
                list.Select(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void SequenceEqual_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act and assert
            try
            {
                list.SequenceEqual(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void SingleOrDefault_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1 };

            // act and assert
            try
            {
                list.SingleOrDefault(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Single_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1 };
            List<int> listEmpty = new List<int>();

            // act and assert
            try
            {
                listEmpty.Single();
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }

            try
            {
                listEmpty.Single(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                list.Single(x => x == 0);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is InvalidOperationException);
            }
        }

        [TestMethod]
        public void SkipWhile_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3, 4, 5 };
            Func<int, bool> func = null;

            // act and assert
            try
            {
                list.SkipWhile(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Sum_abnormal()
        {
            // arrange
            List<int> listInt = new List<int>();
            List<long> listLong = new List<long>();
            List<double> listDouble = new List<double>();
            List<decimal> listBigDecimal = new List<decimal>();

            Func<int, int> funcInt = null;
            Func<long, int> funcLong = null;
            Func<double, int> funcDouble = null;
            Func<decimal, int> funcDecimal = null;

            // act and assert
            try
            {
                listInt.Sum(funcInt);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                listLong.Sum(funcLong);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                listDouble.Sum(funcDouble);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                listBigDecimal.Sum(funcDecimal);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Sum_overflow()
        {
            // arrange
            List<int> listOverflowInt = new List<int>() { int.MaxValue, 1 };
            List<int> listUnderflowInt = new List<int>() { int.MinValue, -1 };
            List<long> listOverflowLong = new List<long>() { long.MaxValue, 1l };
            List<long> listUnderflowLong = new List<long>() { long.MinValue, -1l };

            // act and assert
            try
            {
                listOverflowInt.Sum(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }
            try
            {
                listUnderflowInt.Sum(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }

            try
            {
                listOverflowLong.Sum(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }
            try
            {
                listUnderflowLong.Sum(x => x);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is OverflowException);
            }
        }

        [TestMethod]
        public void TakeWhile_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3, 4, 5 };
            Func<int, int, bool> func = null;

            // act and assert
            try
            {
                list.TakeWhile(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void ThenByDescending_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            Func<String, String> func = null;

            // act and assert
            try
            {
                list.OrderBy(x => x).ThenByDescending(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void ThenBy_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            Func<String, String> func = null;

            // act and assert
            try
            {
                list.OrderBy(x => x).ThenBy(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void ToDictionary_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            Func<String, String> func = null;

            // act and assert
            try
            {
                list.ToDictionary(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void ToDictionarySelect_abnormal()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            Func<String, String> func = null;
            Func<String, String> selector = null;

            // act and assert
            try
            {
                list.ToDictionary(func, selector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Union_abnormal()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };

            // act and assert
            try
            {
                first.Union(null);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Where_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };
            Func<int, bool> func = null;

            // act and assert
            try
            {
                list.Where(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }


        [TestMethod]
        public void Where_Index_abnormal()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };
            Func<int, int, bool> func = null;

            // act and assert
            try
            {
                list.Where(func);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }

        [TestMethod]
        public void Zip_abnormal()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };
            List<String> second = new List<String>() { "Angular", "React", "Backbone" };
            Func<int, String, String> resultSelector = (x, y) => String.Format("{0} {1}", x, y);
            Func<int, String, String> resultSelectorNull = null;

            // act and assert
            try
            {
                first.Zip(null, resultSelector);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }

            try
            {
                first.Zip(second, resultSelectorNull);
                Assert.Fail();
            }
            catch (Exception e)
            {
                Assert.IsTrue(e is ArgumentNullException);
            }
        }
    }
}