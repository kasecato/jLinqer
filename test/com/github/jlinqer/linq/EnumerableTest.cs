﻿using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Com.JLinqer
{
    /// <summary>
    /// Created by Keisuke Kato
    /// </summary>
    [TestClass]
    public class EnumerableTest
    {

        [TestMethod]
        void All()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act
            Boolean actual = list.All(x => x == "Angular" || x == "Backbone" || x == "React");
            Boolean actualNotFound = list.All(x => x == "Angular" || x == "React");

            // assert
            Assert.AreEqual(true, actual);
            Assert.AreEqual(false, actualNotFound);
        }

        [TestMethod]
        void Aggregate()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            int actual = list.Aggregate((sum, elem) => sum + elem);

            // assert
            Assert.AreEqual(6, actual);
        }

        [TestMethod]
        void Any()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            List<String> listEmpty = new List<String>() { };

            // act
            Boolean actual = list.Any(x => x == "Angular");
            Boolean actualNotFound = list.Any(x => x == "jquery");
            Boolean actualNotEmpty = list.Any();
            Boolean actualEmpty = listEmpty.Any();

            // assert
            Assert.AreEqual(true, actual);
            Assert.AreEqual(false, actualNotFound);
            Assert.AreEqual(true, actualNotEmpty);
            Assert.AreEqual(false, actualEmpty);
        }

        [TestMethod]
        void Average()
        {
            // arrange
            List<int> listInt = new List<int>() { 1, 2, 3, 4 };
            List<long> listlong = new List<long>() { 1, 2, 3, 4 };
            List<double> listdouble = new List<double>() { 1d, 2d, 3d, 4d };
            List<decimal> listdecimal = new List<decimal>() {
                new decimal(1d),
                new decimal(2d),
                new decimal(3d),
                new decimal(4d)
        };

            // act
            double actualInt = listInt.Average(x => x);
            double actuallong = listlong.Average(x => x);
            double actualdouble = listdouble.Average(x => x);
            decimal actualdecimal = listdecimal.Average(x => x);

            // assert
            Assert.AreEqual(2.5d, actualInt, 0);
            Assert.AreEqual(2.5d, actuallong, 0);
            Assert.AreEqual(2.5d, actualdouble, 0);
            Assert.AreEqual(new decimal(2.5), actualdecimal);
        }

        [TestMethod]
        void Cast()
        {
            // arrange
            List<Object> list = new List<Object>() { 1, 2, 3 };

            // act
            List<int> actual = list.Cast<int>().ToList();

            // assert
            Assert.AreEqual(1, actual[0]);
            Assert.AreEqual(2, actual[1]);
            Assert.AreEqual(3, actual[2]);
        }

        [TestMethod]
        void Concat()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };
            List<int> listSecond = new List<int>() { 4, 5, 6 };

            // act
            List<int> actual = list.Concat(listSecond).ToList();

            // assert
            Assert.AreEqual(6, actual.Count());
        }

        [TestMethod]
        void Count()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act
            int actual = list.Count();
            int actualOne = list.Count(x => x == "React");
            int actualZero = list.Count(x => x == "jquery");

            long actuallong = list.LongCount();
            long actualOnelong = list.LongCount(x => x == "React");
            long actualZerolong = list.LongCount(x => x == "jquery");

            // assert
            Assert.AreEqual(3, actual);
            Assert.AreEqual(1, actualOne);
            Assert.AreEqual(0, actualZero);

            Assert.AreEqual(3, actuallong);
            Assert.AreEqual(1, actualOnelong);
            Assert.AreEqual(0, actualZerolong);
        }

        [TestMethod]
        void DefaultIfEmpty()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            List<String> listEmpty = new List<String>() { };

            // act
            List<String> actual = list.DefaultIfEmpty().ToList();
            List<String> actualEmpty = listEmpty.DefaultIfEmpty().ToList();
            List<String> actualDefault = listEmpty.DefaultIfEmpty("ES7").ToList();
            List<String> actualDefaultNull = listEmpty.DefaultIfEmpty(null).ToList();

            // assert
            Assert.AreEqual(3, actual.Count);
            Assert.AreEqual(1, actualEmpty.Count);
            Assert.AreEqual("ES7", actualDefault[0]);
            Assert.AreEqual(1, actualDefaultNull.Count());
        }

        [TestMethod]
        void Distinct()
        {
            // arrange
            List<int> list =
                    new List<int>() {
                        1, 2, 3,
                        1, 2, 3, 4
                };

            // act
            List<int> actual = list.Distinct().ToList();

            // assert
            Assert.AreEqual(4, actual.Count());
        }

        [TestMethod]
        void ElementAt()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            int actual = list.ElementAt(2);

            // assert
            Assert.AreEqual(3, actual);
        }

        [TestMethod]
        void ElementAtOrDefault()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            int actual = list.ElementAtOrDefault(2);
            int actualDefault = list.ElementAtOrDefault(3);

            // assert
            Assert.AreEqual(3, actual);
            Assert.AreEqual(0, actualDefault);
        }

        [TestMethod]
        void Empty()
        {
            // act
            List<double> actual = Enumerable.Empty<double>().ToList();

            // assert
            Assert.AreEqual(0, actual.Count());
        }

        [TestMethod]
        void Except()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };
            List<int> second = new List<int>() { 1, 3 };

            // act
            List<int> actual = first.Except(second).ToList();

            // assert
            Assert.AreEqual(1, actual.Count());
            Assert.AreEqual(2, actual[0]);
        }

        [TestMethod]
        void First()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            int actual = list.First();
            int actualMatch = list.First(x => x == 2);

            // assert
            Assert.AreEqual(1, actual);
            Assert.AreEqual(2, actualMatch);
        }

        [TestMethod]
        void FirstOrDefault()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };
            List<String> listEmpty = new List<String>() { };

            // act
            String actualEmpty = listEmpty.FirstOrDefault();
            String actualFirst = list.FirstOrDefault();
            String actualMatch = list.FirstOrDefault(x => x == "Angular");
            String actualUnMatch = list.FirstOrDefault(x => x == "jquery");

            // assert
            Assert.AreEqual(null, actualEmpty);
            Assert.AreEqual("Backbone", actualFirst);
            Assert.AreEqual("Angular", actualMatch);
            Assert.AreEqual(null, actualUnMatch);
        }

        [TestMethod]
        void GroupBy()
        {

            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 1),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
            };

            // act
            var actual = list.GroupBy(x => x.Age).ToList();

            // assert
            Assert.AreEqual(true, actual[0].Any(x => x.Name == "Angular"));
            Assert.AreEqual(true, actual[0].Any(x => x.Name == "React"));
            Assert.AreEqual(false, actual[0].Any(x => x.Name == "Backbone"));
            Assert.AreEqual(false, actual[1].Any(x => x.Name == "Angular"));
            Assert.AreEqual(false, actual[1].Any(x => x.Name == "React"));
            Assert.AreEqual(true, actual[1].Any(x => x.Name == "Backbone"));
        }

        [TestMethod]
        void GroupJoin()
        {
            List<Javascript> outer = new List<Javascript>() {
                    new Javascript("Angular", 1),
                    new Javascript("React", 4),
                    new Javascript("ES2016", 5)
            };
            List<Javascript> inner = new List<Javascript>() {
                    new Javascript("Angular", 2),
                    new Javascript("Angular", 3),
                    new Javascript("ES2016", 6),
                    new Javascript("ES7", 7)
            };

            // act
            Func<Javascript, String> outerKey = (x) => x.Name;
            Func<Javascript, String> innerKey = (y) => y.Name;
            Func<Javascript, IEnumerable<Javascript>, Javascript> selector = (x, y) => new Javascript(x.Name, y.Select(z => z.Age));
            List<Javascript> actual = outer.GroupJoin(inner, outerKey, innerKey, selector).ToList();

            // assert
            Assert.AreEqual(3, actual.Count);
            Assert.AreEqual("Angular", actual.ElementAt(0).Name);
            Assert.AreEqual("React", actual.ElementAt(1).Name);
            Assert.AreEqual("ES2016", actual.ElementAt(2).Name);
            Assert.AreEqual(2, actual.ElementAt(0).Ages.ElementAt(0));
            Assert.AreEqual(3, actual.ElementAt(0).Ages.ElementAt(1));
            Assert.AreEqual(0, actual.ElementAt(1).Ages.Count());
            Assert.AreEqual(6, actual.ElementAt(2).Ages.ElementAt(0));
        }

        [TestMethod]
        void Intersect()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };
            List<int> second = new List<int>() { 1, 3 };

            // act
            List<int> actual = first.Intersect(second).ToList();

            // assert
            Assert.AreEqual(2, actual.Count());
            Assert.AreEqual(1, actual[0]);
            Assert.AreEqual(3, actual[1]);
        }

        [TestMethod]
        void Join()
        {
            List<Javascript> outer = new List<Javascript>() {
                    new Javascript("Angular", 1),
                    new Javascript("React", 4),
                    new Javascript("ES2016", 5)
            };
            List<Javascript> inner = new List<Javascript>() {
                    new Javascript("Angular", 2),
                    new Javascript("Angular", 3),
                    new Javascript("ES2016", 6),
                    new Javascript("ES7", 7)
            };

            // act
            Func<Javascript, String> outerKey = (x) => x.Name;
            Func<Javascript, String> innerKey = (y) => y.Name;
            Func<Javascript, Javascript, Javascript> selector = (x, y) => new Javascript(x.Name, y.Age);
            List<Javascript> actual = outer.Join(inner, outerKey, innerKey, selector).ToList();

            // assert
            Assert.AreEqual(3, actual.Count);
            Assert.AreEqual("Angular", actual.ElementAt(0).Name);
            Assert.AreEqual("Angular", actual.ElementAt(1).Name);
            Assert.AreEqual("ES2016", actual.ElementAt(2).Name);
            Assert.AreEqual(2, actual.ElementAt(0).Age);
            Assert.AreEqual(3, actual.ElementAt(1).Age);
            Assert.AreEqual(6, actual.ElementAt(2).Age);
        }

        [TestMethod]
        void Last()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            int actual = list.Last();
            int actualOne = list.Last(x => x == 1);

            // assert
            Assert.AreEqual(3, actual);
            Assert.AreEqual(1, actualOne);
        }

        [TestMethod]
        void LastOrDefault()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };
            List<int> listEmpty = new List<int>() { };

            // act
            int actual = list.LastOrDefault();
            int actualOne = list.LastOrDefault(x => x == 1);

            int actualDefaultEmpty = listEmpty.LastOrDefault();
            int actualDefaultNone = listEmpty.LastOrDefault(x => x == 0);

            // assert
            Assert.AreEqual(3, actual);
            Assert.AreEqual(1, actualOne);
            Assert.AreEqual(0, actualDefaultEmpty);
            Assert.AreEqual(0, actualDefaultNone);
        }

        [TestMethod]
        void Max()
        {
            // arrange
            List<int> listInt = new List<int>() { 1, 2, 3 };
            List<long> listlong = new List<long>() { 1, 2, 3 };
            List<double> listdouble = new List<double>() { 1d, 2d, 3d };
            List<decimal> listdecimal = new List<decimal>() {
                new decimal(1d),
                new decimal(2d),
                new decimal(3d)
        };

            // act
            double actualInt = listInt.Max(x => x);
            double actuallong = listlong.Max(x => x);
            double actualdouble = listdouble.Max(x => x);
            decimal actualdecimal = listdecimal.Max(x => x);

            // assert
            Assert.AreEqual(3, actualInt, 0);
            Assert.AreEqual(3, actuallong, 0);
            Assert.AreEqual(3d, actualdouble, 0);
            Assert.AreEqual(3, actualdecimal);
        }

        [TestMethod]
        void Min()
        {
            // arrange
            List<int> listInt = new List<int>() { 1, 2, 3 };
            List<long> listlong = new List<long>() { 1, 2, 3 };
            List<double> listdouble = new List<double>() { 1d, 2d, 3d };
            List<decimal> listdecimal = new List<decimal>(){
                new decimal(1d),
                new decimal(2d),
                new decimal(3d)
        };

            // act
            double actualInt = listInt.Min(x => x);
            double actuallong = listlong.Min(x => x);
            double actualdouble = listdouble.Min(x => x);
            decimal actualdecimal = listdecimal.Min(x => x);

            // assert
            Assert.AreEqual(1, actualInt, 0);
            Assert.AreEqual(1, actuallong, 0);
            Assert.AreEqual(1d, actualdouble, 0);
            Assert.AreEqual(1, actualdecimal);
        }

        [TestMethod]
        void OfType()
        {
            // arrange
            List<Object> list = new List<Object>() { 1, "2", 3, "4" };

            // act
            List<String> actualStr = list.OfType<String>().ToList();
            List<int> actualInt = list.OfType<int>().ToList();

            // assert
            Assert.AreEqual("2", actualStr[0]);
            Assert.AreEqual("4", actualStr[1]);
            Assert.AreEqual(1, actualInt[0]);
            Assert.AreEqual(3, actualInt[1]);
        }

        [TestMethod]
        void OrderBy()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act
            List<String> actual = list.OrderBy(x => x).ToList();

            // assert
            Assert.AreEqual("Angular", actual[0]);
            Assert.AreEqual("Backbone", actual[1]);
            Assert.AreEqual("React", actual[2]);
        }

        [TestMethod]
        void OrderByDescending()
        {
            // arrange
            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 3),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
        };

            // act
            List<Javascript> actual = list.OrderByDescending(x => x.Age).ToList();

            // assert
            Assert.AreEqual(5, actual[0].Age);
            Assert.AreEqual(3, actual[1].Age);
            Assert.AreEqual(1, actual[2].Age);
        }

        [TestMethod]
        void Range()
        {
            // act
            List<int> actual = Enumerable.Range(-2, 3).ToList();

            // assert
            Assert.AreEqual(3, actual.Count());
            Assert.AreEqual(-2, actual[0]);
            Assert.AreEqual(0, actual[2]);
        }

        [TestMethod]
        void Repeat()
        {
            // act
            List<String> actual = Enumerable.Repeat<String>("circle", 10).ToList();

            // assert
            Assert.AreEqual(10, actual.Count());
            Assert.AreEqual("circle", actual[9]);
        }

        [TestMethod]
        void Reverse()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            list.Reverse();
            var actual = list;

            // assert
            Assert.AreEqual(3, actual[0]);
            Assert.AreEqual(2, actual[1]);
            Assert.AreEqual(1, actual[2]);
        }

        [TestMethod]
        void Select()
        {
            // arrange
            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 3),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
        };

            // act
            List<int> actual = list.Select(x => x.Age).ToList();

            // assert
            Assert.AreEqual(3, actual[0]);
            Assert.AreEqual(1, actual[1]);
            Assert.AreEqual(5, actual[2]);
        }

        [TestMethod]
        void selectMany()
        {
            // arrange

            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 3, new List<String>(new List<String>() {"1.0.1", "1.0.2"})),
                new Javascript("React", 1, new List<String>(new List<String>() {"2.0.1", "2.0.2"})),
                new Javascript("Backbone", 1, new List<String>(new List<String>() {"3.0.1", "3.0.2"}))
        };

            // act
            List<String> actual = list.SelectMany(x => x.Ver).ToList();

            // assert
            Assert.AreEqual(6, actual.Count());
            Assert.AreEqual("1.0.1", actual[0]);
            Assert.AreEqual("1.0.2", actual[1]);
            Assert.AreEqual("2.0.1", actual[2]);
            Assert.AreEqual("2.0.2", actual[3]);
            Assert.AreEqual("3.0.1", actual[4]);
            Assert.AreEqual("3.0.2", actual[5]);
        }

        [TestMethod]
        void SequenceEqual()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };
            List<int> secondMatch = new List<int>() { 1, 2, 3 };
            List<int> secondUnMatchElem = new List<int>() { 1, 2, 4 };
            List<int> secondUnMatchCount = new List<int>() { 1, 2, 3, 4 };

            // act
            Boolean actualMatch = first.SequenceEqual(secondMatch);
            Boolean actualUnMatchElm = first.SequenceEqual(secondUnMatchElem);
            Boolean actualUnMatchCount = first.SequenceEqual(secondUnMatchCount);

            // assert
            Assert.AreEqual(true, actualMatch);
            Assert.AreEqual(false, actualUnMatchElm);
            Assert.AreEqual(false, actualUnMatchCount);
        }

        [TestMethod]
        void Single()
        {
            // arrange
            List<int> list = new List<int>() { 1 };
            List<int> listMany = new List<int>() { 1, 2, 3 };

            // act
            int actual = list.Single();
            int actualFilter = listMany.Single(x => x == 3);

            // assert
            Assert.AreEqual(1, actual);
            Assert.AreEqual(3, actualFilter);
        }

        [TestMethod]
        void SingleOrDefault()
        {
            // arrange
            List<int> list = new List<int>() { 1 };
            List<int> listMany = new List<int>() { 1, 2, 3 };
            List<int> listEmpty = new List<int>() { };

            // act
            int actual = list.SingleOrDefault();
            int actualFilter = listMany.SingleOrDefault(x => x == 3);
            int actualEmpty = listEmpty.SingleOrDefault();
            int actualUnMatch = listEmpty.SingleOrDefault(x => x == 0);

            // assert
            Assert.AreEqual(1, actual);
            Assert.AreEqual(3, actualFilter);
            Assert.AreEqual(0, actualEmpty);
            Assert.AreEqual(0, actualUnMatch);
        }

        [TestMethod]
        void Skip()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            List<int> actual = list.Skip(2).ToList();
            List<int> actualOver = list.Skip(3).ToList();

            // assert
            Assert.AreEqual(3, actual[0]);
            Assert.AreEqual(0, actualOver.Count());
        }

        [TestMethod]
        void SkipWhile()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3, 4, 5 };

            // act
            List<int> actual = list.SkipWhile(x => x <= 3).ToList();
            List<int> actualAll = list.SkipWhile(x => x == 0).ToList();
            List<int> actualUnMatch = list.SkipWhile(x => x <= 5).ToList();

            // assert
            Assert.AreEqual(4, actual[0]);
            Assert.AreEqual(5, actualAll[4]);
            Assert.AreEqual(0, actualUnMatch.Count());
        }

        [TestMethod]
        void Sum()
        {
            // arrange
            List<int> listInt = new List<int>() { 1, 2, 3 };
            List<long> listlong = new List<long>() { 1, 2, 3 };
            List<double> listdouble = new List<double>() { 1.1d, 2.2d, 3.3d };
            List<decimal> listdecimal = new List<decimal>() {
                new decimal(1.1),
                new decimal(2.2),
                new decimal(3.3)
        };

            // act
            int actualInt = listInt.Sum(x => x);
            long actuallong = listlong.Sum(x => x);
            double actualdouble = listdouble.Sum(x => x);
            decimal actualdecimal = listdecimal.Sum(x => x);

            // assert
            Assert.AreEqual(6, actualInt);
            Assert.AreEqual(6, actuallong);
            Assert.AreEqual(6.6d, actualdouble, 0d);
            Assert.AreEqual(new decimal(6.6), actualdecimal);
        }

        [TestMethod]
        void Take()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act
            List<String> actual = list.Take(2).ToList();
            List<String> actualOver = list.Take(4).ToList();

            // assert
            Assert.AreEqual(2, actual.Count());
            Assert.AreEqual("Backbone", actual[0]);
            Assert.AreEqual("Angular", actual[1]);

            Assert.AreEqual(3, actualOver.Count());
            Assert.AreEqual("React", actualOver[2]);
        }

        [TestMethod]
        void TakeWhile()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act
            List<String> actual = list.TakeWhile(x => x == "Backbone" || x == "Angular").ToList();

            // assert
            Assert.AreEqual(2, actual.Count());
            Assert.AreEqual("Backbone", actual[0]);
            Assert.AreEqual("Angular", actual[1]);
        }

        [TestMethod]
        void ThenBy()
        {
            // arrange
            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular2", 2, "b"),
                new Javascript("Angular2", 2, "a"),
                new Javascript("Angular1", 2, "c"),
                new Javascript("React", 1, "d")
        };

            // act
            List<Javascript> actual = list.OrderBy(x => x.Age).ThenBy(x => x.Name).ThenBy(x => x.Version).ToList();

            // assert
            Assert.AreEqual("d", actual[0].Version);
            Assert.AreEqual("c", actual[1].Version);
            Assert.AreEqual("a", actual[2].Version);
            Assert.AreEqual("b", actual[3].Version);
        }

        [TestMethod]
        void ThenByDescending()
        {
            // arrange
            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular2", 2, "b"),
                new Javascript("Angular2", 2, "a"),
                new Javascript("Angular1", 2, "c"),
                new Javascript("React", 1, "d")
        };

            // act
            List<Javascript> actual = list.OrderByDescending(x => x.Age).ThenByDescending(x => x.Name).ThenByDescending(x => x.Version).ToList();

            // assert
            Assert.AreEqual("b", actual[0].Version);
            Assert.AreEqual("a", actual[1].Version);
            Assert.AreEqual("c", actual[2].Version);
            Assert.AreEqual("d", actual[3].Version);
        }

        [TestMethod]
        void ToArray()
        {
            // arrange
            List<String> list = new List<String>() { "Backbone", "Angular", "React" };

            // act
            String[] actual = list.Where(x => x.Contains("c")).ToArray();

            // assert
            Assert.AreEqual(2, actual.Count());
            Assert.AreEqual("Backbone", actual[0]);
            Assert.AreEqual("React", actual[1]);
        }

        [TestMethod]
        void ToDictionary()
        {
            // arrange
            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 2),
                new Javascript("React", 1)
            };

            // act
            Dictionary<string, Javascript> actual = list.ToDictionary(x => x.Name);

            // assert
            Assert.AreEqual(2, actual["Angular"].Age);
            Assert.AreEqual(1, actual["React"].Age);
        }

        [TestMethod]
        void ToDictionarySelect()
        {
            // arrange
            List<Javascript> list = new List<Javascript>() {
                new Javascript("Angular", 2),
                new Javascript("React", 1)
            };

            // act
            Dictionary<string, int> actual = list.ToDictionary(x => x.Name, x => x.Age);

            // assert
            Assert.AreEqual(2, actual["Angular"]);
            Assert.AreEqual(1, actual["React"]);
        }

        [TestMethod]
        void Union()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };
            List<int> second = new List<int>() { 0, 1, 3, 4 };

            // act
            List<int> actual = first.Union(second).ToList();

            // assert
            Assert.AreEqual(5, actual.Count());
            Assert.AreEqual(1, actual[0]);
            Assert.AreEqual(2, actual[1]);
            Assert.AreEqual(3, actual[2]);
            Assert.AreEqual(0, actual[3]);
            Assert.AreEqual(4, actual[4]);
        }

        [TestMethod]
        void Where()
        {
            // arrange
            List<int> list = new List<int>() { 1, 2, 3 };

            // act
            List<int> actual = list.Where(x => x == 1 || x == 3).ToList();

            // assert
            Assert.AreEqual(true, actual.Contains(1));
            Assert.AreEqual(false, actual.Contains(2));
            Assert.AreEqual(true, actual.Contains(3));
        }

        [TestMethod]
        void WhereIndex()
        {
            // arrange
            List<string> list = new List<string>() { "baCkbone", "reaCt", "angular" };

            // act
            List<string> actual = list.Where((x, index) => x.Contains("C") && index % 2 == 0).ToList();

            // assert
            Assert.AreEqual(true, actual.Contains("baCkbone"));
            Assert.AreEqual(false, actual.Contains("reaCt"));
            Assert.AreEqual(false, actual.Contains("angular"));
        }

        [TestMethod]
        void Zip()
        {
            // arrange
            List<int> first = new List<int>() { 1, 2, 3 };
            List<String> second = new List<String>() { "Angular", "React", "Backbone" };

            // act
            List<String> actual = first.Zip(second, (x, y) => String.Format("{0} {1}", x, y)).ToList();

            // assert
            Assert.AreEqual("1 Angular", actual[0]);
            Assert.AreEqual("2 React", actual[1]);
            Assert.AreEqual("3 Backbone", actual[2]);
        }
    }

    class Javascript
    {
        public String Name { get; set; }
        public int Age { get; set; }
        public IEnumerable<int> Ages { get; set; }
        public String Version { get; set; }
        public List<String> Ver { get; set; }

        public Javascript(String name, int age, String version)
        {
            this.Name = name;
            this.Age = age;
            this.Version = version;
        }

        public Javascript(String name, int age, List<String> ver)
        {
            this.Name = name;
            this.Age = age;
            this.Ver = ver;
        }

        public Javascript(String name, int age)
        {
            this.Name = name;
            this.Age = age;
        }

        public Javascript(String name, IEnumerable<int> ages)
        {
            this.Name = name;
            this.Ages = ages;
        }
    }
}