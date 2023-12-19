package year23

class Day19 {

    data class Part(val categories: Map<Char, Long>) {
        fun update(category: Char, amount: Long): Part {
            val newCategories = categories.plus(category to amount)
            return Part(newCategories)
        }

        fun get(category: Char): Long = categories[category]!!

        companion object {
            fun fromString(input: String): Part {
                val regex = "[xmas]=[0-9]+".toRegex()
                val categories = regex.findAll(input).toList()
                    .map { it.value.split("=") }
                    .associate { (category, amount) -> category.first() to amount.toLong() }
                return Part(categories)
            }
        }
    }
    data class Rule(val category: Char?, val comparison: Char?, val amount: Long?, val result: String) {
        companion object {
            fun fromString(input: String): Rule {
                return if (input.contains(":")) {
                    val comparison = if (input.contains('<')) '<' else '>'
                    val category = input.substringBefore(comparison).first()
                    val amount = input.substringAfter(comparison).substringBefore(':').toLong()
                    val result = input.substringAfter(':')
                    Rule(category, comparison, amount, result)
                } else {
                    Rule(null, null, null, input)
                }
            }
        }
    }
    data class Workflow(val label: String, val rules: List<Rule>) {
        companion object {
            fun fromString(input: String) : Workflow {
                val label = input.substringBefore("{")
                val rules = input.substringAfter("{").dropLast(1).split(",")
                return Workflow(label, rules.map { Rule.fromString(it) })
            }
        }
    }

    companion object {
        fun parseInput(input: String): Pair<List<Workflow>, List<Part>> {
            val (workflowsText, partsText) = input.replace("\r", "").split("\n\n")
            val workflows = workflowsText.lines().map { Workflow.fromString(it) }
            val parts = partsText.lines().map { Part.fromString(it) }
            return workflows to parts
        }

        private fun Part.passes(rule: Rule): Boolean = when (rule.comparison) {
            null -> true
            '<' -> categories[rule.category]!! < rule.amount!!
            '>' -> categories[rule.category]!! > rule.amount!!
            else -> throw RuntimeException("invalid rule $rule")
        }

        private fun Part.resultFromWorkflow(workflow: Workflow): String {
            workflow.rules.forEach { rule ->
                if (this.passes(rule)) return rule.result
            }
            throw RuntimeException("invalid workflow $workflow")
        }

        private fun Part.accepted(workflows: Map<String, Workflow>): Boolean {
            var result = "in"
            var workflow: Workflow
            do {
                workflow = workflows[result]!!
                result = resultFromWorkflow(workflow)
            } while (result != "A" && result != "R")
            return result == "A"
        }

        fun acceptedSum(input: String): Long {
            val (workflows, parts) = parseInput(input)
            val workflowsMap = workflows.associateBy { it.label }
            return parts.filter { it.accepted(workflowsMap) }.sumOf { it.categories.values.sum() }
        }

        fun validCombinations(input: String): Long {
            val (workflows, _) = parseInput(input)
            val workflowsMap = workflows.associateBy { it.label }
            val minPart = Part(mapOf('x' to 1, 'm' to 1, 'a' to 1, 's' to 1))
            val maxPart = Part(mapOf('x' to 4000, 'm' to 4000, 'a' to 4000, 's' to 4000))
            return combinations(workflowsMap["in"]!!.rules, minPart, maxPart, workflowsMap)
        }

        private fun combinations(
            rules: List<Rule>,
            minPart: Part,
            maxPart: Part,
            labelsToWorkflows: Map<String, Workflow>,
        ): Long {
            val rule = rules.first()
            val comparison = rule.comparison
            val result = rule.result

            if (comparison == null) {
                return safeComboReturn(result, minPart, maxPart, labelsToWorkflows)
            }

            if (minPart.passes(rule) && maxPart.passes(rule)) {
                return safeComboReturn(result, minPart, maxPart, labelsToWorkflows)
            }

            if (!minPart.passes(rule) && !maxPart.passes(rule)) {
                return combinations(rules.drop(1), minPart, maxPart, labelsToWorkflows)
            }

            val category = rule.category!!
            val amount = rule.amount!!

            if (minPart.passes(rule)) { //just min passes, comparison guaranteed to be <
                val success = safeComboReturn(result, minPart, maxPart.update(category, amount - 1), labelsToWorkflows)
                val failure = combinations(rules.drop(1), minPart.update(category, amount), maxPart, labelsToWorkflows)
                return success + failure
            }

            if (maxPart.passes(rule)) { //just max passes, comparison guaranteed to be >
                val success = safeComboReturn(result, minPart.update(category, amount + 1), maxPart, labelsToWorkflows)
                val failure = combinations(rules.drop(1), minPart, maxPart.update(category, amount), labelsToWorkflows)
                return success + failure
            }

            throw RuntimeException("unreachable")
        }

        private fun safeComboReturn(
            result: String,
            minPart: Part,
            maxPart: Part,
            labelsToWorkflows: Map<String, Workflow>
        ) = when (result) {
            "A" -> minPart.categories.map { (c, l) -> maxPart.categories[c]!! - l + 1 }.reduce { acc, l -> acc * l }
            "R" -> 0
            else -> combinations(labelsToWorkflows[result]!!.rules, minPart, maxPart, labelsToWorkflows)
        }
    }
}