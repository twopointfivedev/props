package net.property.process;

import net.property.crawler.HomesandpropertyInjector;
import net.property.crawler.LettingwebInjector;
import net.property.crawler.MoveToURLInjector;
import net.property.crawler.NeedapropertyInjector;
import net.property.crawler.NethousepricesInjector;
import net.property.crawler.PrimeLocationURLInjector;
import net.property.crawler.RightmoveInjector;
import net.property.crawler.SmartnewhomesInjector;
import net.property.crawler.WhathouseURLInjector;
import net.property.crawler.ZooplaInjector;


public class CrawlingController implements Runnable {
	
	@Override
	public void run() {		
		/**
		 *  Zoopla based sites
		 */
/*		new Thread() {
			public void run () {
				new ZooplaInjector().start();
			}
		}.start();*/

		/*new Thread() {
			public void run () {
				new PrimeLocationURLInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new HomesandpropertyInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new SmartnewhomesInjector().start();
			}
		}.start();
		
		*//**
		 *  Zoopla based sites
		 *//*
		new Thread() {
			public void run () {
				new NeedapropertyInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new MoveToURLInjector().start();
			}
		}.start();


		new Thread() {
			public void run () {
				new WhathouseURLInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new NethousepricesInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new NeedapropertyInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new LettingwebInjector().start();
			}
		}.start();
		
		new Thread() {
			public void run () {
				new RightmoveInjector().start();
			}
		}.start();*/
	}
}
