//
// Created by DevilWwj on 16/4/30.
//

//
// Created by wwj_748 on 2016/4/27.
//
#include <stdio.h>
#include <stdlib.h>
#include "com_devilwwj_jnidemo_TestJNI.h"
#include "CAdd.h"

CAdd *pCAdd = NULL;

JNIEXPORT jboolean JNICALL Java_com_devilwwj_jnidemo_TestJNI_Init(JNIEnv *env, jobject obj) {
  if (pCAdd == NULL) {
    pCAdd = new CAdd();
  }
  return pCAdd != NULL;
  }

JNIEXPORT jint JNICALL Java_com_devilwwj_jnidemo_TestJNI_Add
  (JNIEnv *env, jobject obj, jint x, jint y) {
    int res = -1;
    if (pCAdd != NULL) {
        res = pCAdd->Add(x, y);
    }
    return res;
  }

  JNIEXPORT void JNICALL Java_com_devilwwj_jnidemo_TestJNI_destory
    (JNIEnv *env, jobject obj) {
    if (pCAdd != NULL) {
        pCAdd = NULL;
    }
    }