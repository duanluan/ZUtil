import React from 'react';
import clsx from 'clsx';
import styles from './styles.module.css';

type FeatureItem = {
  title: string;
  // Svg: React.ComponentType<React.ComponentProps<'svg'>>;
  description: JSX.Element;
};

const FeatureList: FeatureItem[] = [
  {
    title: '更快',
    // Svg: require('@site/static/img/.svg').default,
    description: (
      <>
        使用 JMH 进行性能测试。<br/>
        <a href='https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/jmh' target='_blank'>https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/jmh</a>
      </>
    ),
  },
  {
    title: '更全',
    // Svg: require('@site/static/img/.svg').default,
    description: (
      <>
        时间工具类近 120 个方法，2500+ 行；<br/>正则工具类近 50 个方法，750+ 行。<br/>
        其他工具类也在持续更新中……
      </>
    ),
  },
  {
    title: '更安全',
    // Svg: require('@site/static/img/.svg').default,
    description: (
      <>
        使用 <a href='https://junit.org/junit5/' target='_blank'>JUnit</a> 进行套件测试，<a href='https://www.jacoco.org/jacoco/index.html' target='_blank'>JaCoCo</a> 进行代码覆盖率测试，保证每行代码都符合预期，更少出 BUG。<br/>
        <a href='https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/junit' target='_blank'>https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/zhogjianhao/junit</a>
      </>
    ),
  },
];

function Feature({title,/* Svg,*/ description}: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      {/*<div className="text--center">*/}
      {/*  <Svg className={styles.featureSvg} role="img" />*/}
      {/*</div>*/}
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): JSX.Element {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
