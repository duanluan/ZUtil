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
        使用 <a href='https://openjdk.org/projects/code-tools/jmh/' target='_blank'>JMH</a> 进行<a href='https://github.com/duanluan/ZUtil/tree/main/src/test/java/top/csaf/jmh' target='_blank'>性能测试</a>。
      </>
    ),
  },
  {
    title: '更全',
    // Svg: require('@site/static/img/.svg').default,
    description: (
      <>
        <a href='https://github.com/duanluan/ZUtil/blob/main/zutil-date/src/main/java/top/csaf/date/DateUtil.java' target='_blank'>时间工具类</a> 130+ 个方法，2700+ 行；<br/>
        <a href='https://github.com/duanluan/ZUtil/blob/main/zutil-regex/src/main/java/top/csaf/regex/RegExUtil.java' target='_blank'>正则工具类</a> 80+ 个方法，1100+ 行。<br/>
      </>
    ),
  },
  {
    title: '更安全',
    // Svg: require('@site/static/img/.svg').default,
    description: (
      <>
        使用 <a href='https://junit.org/junit5' target='_blank'>JUnit</a> 进行套件测试，<a href='https://www.jacoco.org/jacoco/index.html' target='_blank'>JaCoCo</a> 进行<a href='https://github.com/duanluan/ZUtil/tree/main/zutil-all/src/test/java/top/csaf/junit'>代码覆盖率测试</a>，保证每行代码都符合预期，更少出 BUG。<br/>
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
