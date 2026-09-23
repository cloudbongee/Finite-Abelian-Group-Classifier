If a finite group is Abelian, then it generates itself, so it is finitely generated. Therefore we may apply

$$ G \cong \mathbb{Z}^n \oplus \mathbb{Z}/d_1\mathbb{Z} \oplus \cdots \mathbb{Z}/d_k \mathbb{Z} $$

Where $d_i | d_{i+1}$ for every index $i < k$. And to match the cardinality $\prod_{i=1}^{k}d_i = |G|$

Since the group is finite, the rank $n$ is $0$, else the group would be infinite. Reducing the problem of classifying the finite Abelian groups to finding the valid $(d_i)_{i=1}^k$ sequences.

This program's purpose is to output the classifications of these groups.
